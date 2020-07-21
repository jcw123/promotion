package com.im.sky.netty.test.test2.transport;

import com.im.sky.netty.test.test2.error.RpcException;
import com.im.sky.netty.test.test2.msg.ConnectListener;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.server.BaseServerHandler;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.NetUtils;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:28
 **/
@Slf4j
@ChannelHandler.Sharable
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

    private ServerTransportConfig transportConfig;

    private BaseServerHandler serverHandler;

    private final List<ConnectListener> connectListeners;

    public ServerChannelHandler(ServerTransportConfig serverTransportConfig) {
        this.transportConfig = serverTransportConfig;
        this.connectListeners = serverTransportConfig.getConnectListeners();
        serverHandler = BaseServerHandler.getInstance(transportConfig);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        BaseServerHandler.addChannel(channel);
        if(connectListeners != null) {
            serverHandler.getBizThreadPool().execute(() -> {
                for(ConnectListener listener : connectListeners) {
                    try {
                        listener.connected(ctx);
                    }catch (Exception e) {
                        log.warn("failed to call connect listener when channel active", e);
                    }
                }
            });
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("Disconnected from {}", NetUtils.channelToString(channel.remoteAddress(), channel.localAddress()));
        BaseServerHandler.removeChannel(channel);
        if (connectListeners != null) {
            serverHandler.getBizThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    for (ConnectListener connectListener : connectListeners) {
                        try {
                            connectListener.disconnected(ctx);
                        } catch (Exception e) {
                            log.warn("Failed to call connect listener when channel inactive", e);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        if(msg instanceof RequestMessage) {
            RequestMessage requestMessage = (RequestMessage)msg;
            serverHandler.handlerRequest(channel, requestMessage);
        }else {
            throw new RpcException("Only support base message");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        if(cause instanceof IOException) {
            log.warn("catch IOException at{}:{}", NetUtils.channelToString(channel.remoteAddress(), channel.localAddress()), cause.getMessage());
        }else if(cause instanceof RpcException) {
            RpcException rpc = (RpcException) cause;
            MessageHeader header = rpc.getMsgHeader();
            if (header != null) {
                ResponseMessage responseMessage = new ResponseMessage();
                responseMessage.getMsgHeader().copyHeader(header);
                responseMessage.getMsgHeader().setMsgType(Constants.RESPONSE_MSG);
                String causeMsg = cause.getMessage();
                String channelInfo = BaseServerHandler.getKey(ctx.channel());
                String causeMsg2 = "Remote Error Channel:" + channelInfo + " cause: " + causeMsg;
                ((RpcException) cause).setErrorMsg(causeMsg2);
                responseMessage.setException(cause);
                ChannelFuture channelFuture = ctx.writeAndFlush(responseMessage);
                channelFuture.addListener(new ChannelFutureListener() {

                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            if(log.isTraceEnabled()) {
                                log.trace("have write the error message back to clientside..");
                            }
                            return;
                        } else {
                            log.error("fail to write error back status: {}", future.isSuccess());

                        }
                    }
                });
            }
        }else {
            log.warn("catch " + cause.getClass().getName() + " at {} : {}",
                    NetUtils.channelToString(channel.remoteAddress(), channel.localAddress()),
                    cause.getMessage());
        }
    }

    public BaseServerHandler getServerHandler() {
        return serverHandler;
    }
}
