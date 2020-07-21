package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.error.JoyCodecException;
import com.im.sky.netty.test.test2.error.RpcException;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.transport.ServerTransportConfig;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.NetUtils;
import io.netty.channel.Channel;
import jdk.nashorn.internal.scripts.JO;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:32
 **/
@Slf4j
public class BaseServerHandler implements ServerHandler {

    /**
     *  one Invoker for one interface + one alias
     */
    private Map<String, Invoker> instanceMap = new ConcurrentHashMap<String, Invoker>();

    /**
     *  one port for a BaseServerHandler instance
     */
    private static Map<String,BaseServerHandler> serverHandlerMap = new ConcurrentHashMap<String, BaseServerHandler>();

    /**
     * business thread pool(one port for a thread pool)
     */
    private final ExecutorService bizThreadPool;

    private final ServerTransportConfig serverTransportConfig;

    public BaseServerHandler(ServerTransportConfig transportConfig){
        this.serverTransportConfig = transportConfig;
        this.bizThreadPool = BusinessPool.getBusinessPool(this.serverTransportConfig);
    }

    public synchronized static BaseServerHandler getInstance(ServerTransportConfig transportConfig) {
        BaseServerHandler serverHandler;
        serverHandler = serverHandlerMap.get(transportConfig.getServerTransportKey());
        if (serverHandler == null) {
            serverHandler = new BaseServerHandler(transportConfig);
            serverHandlerMap.put(transportConfig.getServerTransportKey(), serverHandler);
        }
        return serverHandler;
    }

    public static Invoker getInvoker(String interfaceId, String alias) {
        return InvokerHolder.getInvoker(interfaceId, alias);
    }

    /**
     * 长连接列表
     */
    private static final ConcurrentHashMap<String, Channel> channelsMap = new ConcurrentHashMap<String, Channel>();


    @Override
    public void registerProcessor(String instanceName, Invoker invoker) {
        instanceMap.put(instanceName, invoker);
        InvokerHolder.cacheInvoker(instanceName, invoker);
    }

    @Override
    public void unregisterProcessor(String instanceName) {
        if (instanceMap.containsKey(instanceName)) {
            instanceMap.remove(instanceName);
            InvokerHolder.invalidateInvoker(instanceName);
        } else {
            throw new RuntimeException("No such invoker key when unregister processor:" + instanceName);
        }
    }

    public Invoker getOwnInvoker(String interfaceId, String alias) {
        String key = genInstanceName(interfaceId, alias);
        return instanceMap.get(key);
    }

    public static Map<String, Invoker> getAllInvoker() {
        return new HashMap<String, Invoker>(InvokerHolder.getAllInvoker());
    }

    public Map<String, Invoker> getAllOwnInvoker() {
        return instanceMap;
    }

    public static String genInstanceName(String interfaceId,String alias){
        if( interfaceId == null || interfaceId.trim().length() <= 0){
            throw new RpcException("interfaceId cannot be null!");
        }
        if( alias == null || alias.trim().length() <=0){
            return interfaceId;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(interfaceId);
        builder.append("/");
        builder.append(alias);
        return builder.toString();
    }

    @Override
    public void handlerRequest(Channel channel, Object requestMsg) {
        RequestMessage msg = (RequestMessage)requestMsg;
        try {

            BaseTask task = getHandleTask(channel,msg);
            submitTask(task);
            // TODO task的优先级设置 此时未反序列化，不知道接口和方法等信息
        } catch (Exception e) {
            throw new RpcException(msg.getMsgHeader(), e);
        }
    }

    protected void submitTask(BaseTask task){
        bizThreadPool.submit(task);
    }

    public static String getKey(Channel channel){
        InetSocketAddress local = (InetSocketAddress)channel.localAddress();
        InetSocketAddress address = (InetSocketAddress)channel.remoteAddress();
        StringBuilder sb = new StringBuilder();
        sb.append(NetUtils.toIpString(address));
        sb.append(":");
        sb.append(address.getPort());
        sb.append(" --> ");
        sb.append(NetUtils.toIpString(local));
        sb.append(":");
        sb.append(local.getPort());

        String key = sb.toString();
        return key;
    }

    private BaseTask getHandleTask(final Channel channel, final RequestMessage msg) {
        Constants.ProtocolType protocolType = Constants.ProtocolType.valueOf(msg.getProtocolType());
        switch (protocolType) {
            case JOY:
                return new JoyTask(this, msg, channel);
            default:
                throw new JoyCodecException("Unsupported protocol type of task:" + protocolType);
        }
    }

    public static List<Channel> getAllChannel(){
        Collection<Channel> channels = channelsMap.values();
        List<Channel> channelList = new ArrayList<Channel>(channels);
        return channelList;
    }

    public static Channel getChannelByKey(String key){
        return channelsMap.get(key);
    }

    public ExecutorService getBizThreadPool() {
        return bizThreadPool;
    }

    public ServerTransportConfig getServerTransportConfig() {
        return serverTransportConfig;
    }

    @Override
    public void shutdown() {
        if(!bizThreadPool.isShutdown()) {
            log.debug("ServerHandler's business thread pool shutdown..");
            bizThreadPool.shutdown();
        }
    }

    public static void addChannel(Channel channel){
        String key = getKey(channel);
        channelsMap.put(key,channel);
    }

    public static Channel removeChannel(Channel channel){
        String key = getKey(channel);
        return channelsMap.remove(key) ;
    }
}
