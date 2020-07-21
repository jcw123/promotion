package com.im.sky.netty.test.test2.transport;

import com.im.sky.netty.test.test2.msg.ConnectListener;
import com.im.sky.netty.test.test2.util.Constants;
import com.im.sky.netty.test.test2.util.NamedThreadFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2020-6-16 上午 9:55
 **/
public class ServerTransportConfig {

    private int port = 66666;

    private String host = "localhost";

    private String contextPath;

    private static int BACKLOG = 35536;

    private boolean TCP_NO_DELAY = true;

    private boolean REUSE_ADDR = true;

    private boolean KEEPALIVE = true;

    private int CONNECT_TIMEOUT = 5000;

    /**
     * server side timeout config default
     */
    private int TIMEOUT = 2000;

    private int serverBusinessPoolSize = 200;

    private String serverBusinessPoolType = Constants.THREAD_POOL_TYPE_CACHED;

    public boolean useEpoll = false;

    private String poolQueueType = Constants.QUEUE_TYPE_NORMAL;

    private int poolQueueSize = 0;

    private int parentNioEventThreads = 0;

    private int childNioEventThreads = 0;

    private List<ConnectListener> connectListeners;

    private int maxConnection = 100;

    /**
     * 最大的数据包大小
     */
    private int payload = 8 * 1024 * 1024;

    /**
     * 缓存器的大小
     */
    private int buffer;

    /**
     * 线程方法模型
     */
    private String dispatcher;

    /**
     * 是否守护线程，true：随着主线程退出；false：主动退出
     */
    private boolean daemon = true;

    private Map<String, String> parameters;

    private Constants.ProtocolType protocolType = Constants.DEFAULT_PROTOCOL_TYPE;

    private static Map<Integer, EventLoopGroup> parentGroupMap = new ConcurrentHashMap<>();

    private static Map<Integer, EventLoopGroup> childGroupMap = new ConcurrentHashMap<>();

    private synchronized EventLoopGroup initParentEventLoopGroup() {
        int threads;
        if(parentNioEventThreads == 0) {
            threads = 4;
        }else {
            threads = parentNioEventThreads;
        }
        NamedThreadFactory threadFactory = new NamedThreadFactory("JOY-SEV-BOSS", isDaemon());
        EventLoopGroup eventLoopGroup;
        if(this.isUseEpoll()) {
            eventLoopGroup = new EpollEventLoopGroup(threads, threadFactory);
        }else {
            eventLoopGroup = new NioEventLoopGroup(threads, threadFactory);
        }
        return eventLoopGroup;
    }

    private synchronized EventLoopGroup initChildEventLoopGroup(){
        int threads = childNioEventThreads > 0 ?
                childNioEventThreads :
                Math.max(8, Constants.DEFAULT_IO_THREADS);
        NamedThreadFactory threadName = new NamedThreadFactory("JOY-SEV-WORKER", isDaemon());
        EventLoopGroup eventLoopGroup;
        if(this.isUseEpoll()){
            eventLoopGroup = new EpollEventLoopGroup(threads,threadName);
        }else{
            eventLoopGroup = new NioEventLoopGroup(threads,threadName);
        }
        return eventLoopGroup;
    }

    public EventLoopGroup getParentEventLoopGroup(){
        EventLoopGroup parent = parentGroupMap.get(protocolType.value()) ;
        if(parent == null){
            parent = initParentEventLoopGroup();
            parentGroupMap.put(protocolType.value(),parent);
        }
        return parent;
    }

    public EventLoopGroup getChildEventLoopGroup(){
        EventLoopGroup child = childGroupMap.get(protocolType.value()) ;
        if(child == null){
            child = initChildEventLoopGroup();
            childGroupMap.put(protocolType.value(),child);
        }
        return child;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getBACKLOG() {
        return BACKLOG;
    }

    public void setBACKLOG(int BACKLOG) {
        ServerTransportConfig.BACKLOG = BACKLOG;
    }

    public boolean isTCP_NO_DELAY() {
        return TCP_NO_DELAY;
    }

    public void setTCP_NO_DELAY(boolean TCP_NO_DELAY) {
        this.TCP_NO_DELAY = TCP_NO_DELAY;
    }

    public boolean isREUSE_ADDR() {
        return REUSE_ADDR;
    }

    public void setREUSE_ADDR(boolean REUSE_ADDR) {
        this.REUSE_ADDR = REUSE_ADDR;
    }

    public boolean isKEEPALIVE() {
        return KEEPALIVE;
    }

    public void setKEEPALIVE(boolean KEEPALIVE) {
        this.KEEPALIVE = KEEPALIVE;
    }

    public int getCONNECT_TIMEOUT() {
        return CONNECT_TIMEOUT;
    }

    public void setCONNECT_TIMEOUT(int CONNECT_TIMEOUT) {
        this.CONNECT_TIMEOUT = CONNECT_TIMEOUT;
    }

    public int getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(int TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public int getServerBusinessPoolSize() {
        return serverBusinessPoolSize;
    }

    public void setServerBusinessPoolSize(int serverBusinessPoolSize) {
        this.serverBusinessPoolSize = serverBusinessPoolSize;
    }

    public String getServerBusinessPoolType() {
        return serverBusinessPoolType;
    }

    public void setServerBusinessPoolType(String serverBusinessPoolType) {
        this.serverBusinessPoolType = serverBusinessPoolType;
    }

    public boolean isUseEpoll() {
        return useEpoll;
    }

    public void setUseEpoll(boolean useEpoll) {
        this.useEpoll = useEpoll;
    }

    public String getPoolQueueType() {
        return poolQueueType;
    }

    public void setPoolQueueType(String poolQueueType) {
        this.poolQueueType = poolQueueType;
    }

    public int getPoolQueueSize() {
        return poolQueueSize;
    }

    public void setPoolQueueSize(int poolQueueSize) {
        this.poolQueueSize = poolQueueSize;
    }

    public int getParentNioEventThreads() {
        return parentNioEventThreads;
    }

    public void setParentNioEventThreads(int parentNioEventThreads) {
        this.parentNioEventThreads = parentNioEventThreads;
    }

    public int getChildNioEventThreads() {
        return childNioEventThreads;
    }

    public void setChildNioEventThreads(int childNioEventThreads) {
        this.childNioEventThreads = childNioEventThreads;
    }

    public List<ConnectListener> getConnectListeners() {
        return connectListeners;
    }

    public void setConnectListeners(List<ConnectListener> connectListeners) {
        this.connectListeners = connectListeners;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Constants.ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(Constants.ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public String getServerTransportKey() {
        String key = host + ":" + port;
        return key;
    }
}
