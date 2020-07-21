package com.im.sky.netty.test.test2.client;

import com.im.sky.netty.test.test2.error.ClientTimeoutException;
import com.im.sky.netty.test.test2.error.RpcException;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.protocol.Protocol;
import com.im.sky.netty.test.test2.protocol.ProtocolFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:10
 **/
public class MsgFuture<V> implements Future {

    private List<ResultListener> listeners = new ArrayList<>();

    private volatile Object result;

    private short waiters;

    private static final String DEFAULT_RESULT = "DEFAULT_RESULT";
    private final Channel channel;

    private final MessageHeader header;


    private final int timeout;

    private final long genTime = System.currentTimeMillis();

    private volatile  long sendTime;

    private boolean asyncCall;

    private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(new CancellationException());

    public MsgFuture(Channel channel, MessageHeader msgHeader, int timeout) {
        this.channel = channel;
        this.header = msgHeader;
        this.timeout = timeout;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        Object result = this.result;
        if(isDone0(result) || result == DEFAULT_RESULT) {
            return false;
        }
        synchronized (this) {
            result = this.result;
            if(isDone0(result) || result == DEFAULT_RESULT) {
                return false;
            }
            this.result = CANCELLATION_CAUSE_HOLDER;
            if(hasWaiters()) {
                notifyAll();
            }
        }
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone0(result);
    }

    @Override
    public Object get() throws InterruptedException {
        return get(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException{
        timeout = unit.toMillis(timeout);
        long remainTime = timeout - (sendTime - genTime);
        if(remainTime <= 0) {
            if(isDone()) {
                return getNow();
            }
        }else {
            if(await(remainTime, TimeUnit.MILLISECONDS)) {
                return getNow();
            }
        }
        return new ClientTimeoutException();
    }

    private static boolean isDone0(Object result) {
        return result != null && DEFAULT_RESULT != result;
    }

    private boolean hasWaiters() {
        return waiters > 0;
    }

    public MsgFuture<V> setSuccess(V result) {
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    public boolean await(long timeout, TimeUnit unit)
            throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
        if(isDone()) {
            return  true;
        }
        if(timeoutNanos <= 0) {
            return isDone();
        }
        if(interruptable && Thread.interrupted()) {
            throw new InterruptedException(toString());
        }
        long startTime = System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;
        try {
            synchronized (this) {
                if(isDone()) {
                    return true;
                }
                if(waitTime <=  0) {
                    return isDone();
                }
                incWaiters();
                try {
                    for(;;) {
                        try {
                            wait(waitTime / 1000000, (int)(waitTime % 1000000));
                        }catch (InterruptedException e) {
                            if(interruptable) {
                                throw e;
                            }else {
                                interrupted = true;
                            }
                        }
                        if(isDone()) {
                            return true;
                        }else {
                            waitTime = timeoutNanos - (System.nanoTime() - startTime);
                            if(waitTime <= 0) {
                                return isDone();
                            }
                        }
                    }
                }finally {
                    decWaiters();
                }
            }
        }finally {
            if(interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean setSuccess0(V result) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            // Allow only once.
            if (isDone()) {
                return false;
            }
            if (this.result == null) {

                this.result = result;
            }
            if (hasWaiters()) {
                notifyAll();
            }
        }
        return true;
    }

    private void incWaiters() {
        if (waiters == Short.MAX_VALUE) {
            throw new IllegalStateException("too many waiters: " + this);
        }
        waiters ++;
    }

    private void decWaiters() {
        waiters --;
    }

    public MsgFuture<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, cause);
    }

    private boolean setFailure0(Throwable cause) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            // Allow only once.
            if (isDone()) {
                return false;
            }

            result = new CauseHolder(cause);
            if (hasWaiters()) {
                notifyAll();
            }
        }
        return true;
    }

    public MsgFuture addListener(ResultListener listener){

        if (listener == null) {
            throw new NullPointerException("listener");
        }

        if (isDone()) {
            notifyListener0(listener);
            return this;
        }

        synchronized (this) {
            if (!isDone()) {
                listeners.add(listener);
                return this;
            }
        }

        notifyListener0(listener);
        return this;
    }

    public boolean isAsyncCall() {
        return asyncCall;
    }

    public V getNow() {
        Object result = this.result;
        if(result instanceof ResponseMessage) {
            ResponseMessage tmp = (ResponseMessage)result;
            if(tmp.getMsgBody() != null) {
                Protocol protocol = ProtocolFactory.getProtocol(tmp.getProtocolType(), tmp.getMsgHeader().getCodecType());
                try {
                    ResponseMessage ins = (ResponseMessage) protocol.decode(tmp.getMsgBody(), ResponseMessage.class.getCanonicalName());
                    if (ins.getResponse() != null) {
                        tmp.setResponse(ins.getResponse());
                    } else if (ins.getException() != null) {
                        tmp.setException(ins.getException());
                    }
                }finally {
                    if(tmp.getMsgBody() != null) {
                        tmp.getMsgBody().release();
                    }
                    tmp.setMsgBody(null);
                }
            }
        }else if(result instanceof CauseHolder) {
            Throwable e = ((CauseHolder) result).cause;
            if (e instanceof RpcException) {
                RpcException rpcException = (RpcException) e;
                rpcException.setMsgHeader(header);
                throw rpcException;
            } else {
                throw new RpcException(this.header, ((CauseHolder) result).cause);
            }
        }
        return (V)result;
    }

    private void notifyListeners() {
        if (listeners == null || listeners.isEmpty()) {
            if ( isAsyncCall() ){
                getNow();
            }
            return;
        }
        for (ResultListener resultListener : listeners) {
            notifyListener0(resultListener);
        }
    }

    public void releaseIfNeed() {
        if(result instanceof ResponseMessage){
            ByteBuf byteBuf = ((ResponseMessage) result).getMsgBody();
            if(byteBuf != null && byteBuf.refCnt() > 0 ){
                byteBuf.release();
            }
        }
    }

    private void notifyListener0(final  ResultListener listener) {
        listener.operationComplete(this);
    }

    private static final class CauseHolder {
        final Throwable cause;
        private CauseHolder(Throwable cause) {
            this.cause = cause;
        }
    }
}
