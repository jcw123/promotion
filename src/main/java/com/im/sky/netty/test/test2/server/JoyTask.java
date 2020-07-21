package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.error.RpcException;
import com.im.sky.netty.test.test2.msg.Invocation;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.protocol.Protocol;
import com.im.sky.netty.test.test2.protocol.ProtocolFactory;
import com.im.sky.netty.test.test2.protocol.ProtocolUtil;
import com.im.sky.netty.test.test2.transport.PooledBufHolder;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.NetUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:30
 **/
@Slf4j
public class JoyTask extends BaseTask {

    private final BaseServerHandler serverHandler;

    private final RequestMessage msg;

    private final Channel channel;

    public JoyTask(BaseServerHandler handler, RequestMessage msg, Channel channel) {
        this.serverHandler = handler;
        this.msg = msg;
        this.channel = channel;
    }

    @Override
    protected void doRun() {
        try {
            long now = System.currentTimeMillis();
            Integer timeout = msg.getClientTimeout();
            if(timeout != null && now - msg.getReceiveTime() > timeout) {
                log.warn("discard request cause by timeout after receive the msg:{}", msg.getMsgHeader());
                return;
            }
            Protocol protocol = ProtocolFactory.getProtocol(msg.getProtocolType(), msg.getMsgHeader().getCodecType());
            Invocation invocation;
            invocation = (Invocation)protocol.decode(msg.getMsgBody(), Invocation.class.getCanonicalName());
            msg.setInvocationBody(invocation);
            String className = msg.getClassName();
            String methodName = msg.getMethodName();
            String alias = msg.getAlias();
            Invoker invoker = serverHandler.getOwnInvoker(className, alias);
            final InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
            final InetSocketAddress localAddress = (InetSocketAddress) channel.localAddress();
            if(invoker == null) {
                throw new RpcException("cannot find invoker");
            }
            ResponseMessage responseMessage = invoker.invoke(msg);
            ByteBuf buf = PooledBufHolder.getBuffer();
            buf = ProtocolUtil.encode(responseMessage, buf);
            responseMessage.setMsg(buf);
            ChannelFuture future = channel.writeAndFlush(responseMessage);
            future.addListener((f) -> {
                if(f.isSuccess()) {
                    log.trace("Response write back true");
                }else {
                    Throwable throwable = future.cause();
                    log.error("failed to send response to {} for msg id:{}, caused by:{}",
                            NetUtils.channelToString(localAddress, remoteAddress), msg.getMsgHeader().getMsgId(), throwable);
                }
            });
        }catch (Exception e) {
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMsgHeader(msg.getMsgHeader());
            responseMessage.getMsgHeader().setMsgType(Constants.RESPONSE_MSG);
            responseMessage.setException(e);
            ChannelFuture channelFuture = channel.writeAndFlush(responseMessage);
            channelFuture.addListener((f) -> {
                if(f.isSuccess()) {
                    log.trace("have write the error message back to clientside");
                }else {
                    Throwable throwable = channelFuture.cause();
                    log.error("failed to send error to remote for msg id:{}, caused by:{}", msg.getMsgHeader().getMsgId(),
                            throwable);
                }
            });
        }finally {
            ByteBuf byteBuf = msg.getMsgBody();
            if(byteBuf != null) {
                byteBuf.release();
            }
        }
    }
}
