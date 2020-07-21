package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.util.CommonUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:10
 **/
public class InvokerHolder {

    /**
     * an interface + an alias corresponds to an instance
     */
    private final static ConcurrentHashMap<String, Invoker> allInstanceMap = new ConcurrentHashMap<String, Invoker>();

    private final static ConcurrentHashMap<String, AtomicInteger> refCounter = new ConcurrentHashMap<String, AtomicInteger>();

    public static void cacheInvoker(String key, Invoker invoker) {
        allInstanceMap.putIfAbsent(key, invoker);
        AtomicInteger cnt = CommonUtils.putToConcurrentMap(refCounter, key, new AtomicInteger(0));
        cnt.incrementAndGet();
    }

    public static void invalidateInvoker(String key) {
        AtomicInteger cnt = refCounter.get(key);
        if (cnt != null && cnt.decrementAndGet() <= 0) {
            allInstanceMap.remove(key);
            refCounter.remove(key);
        }
    }

    public static Map<String, Invoker> getAllInvoker() {
        return allInstanceMap;
    }

    public static Invoker getInvoker(String interfaceId, String alias) {
        String key = BaseServerHandler.genInstanceName(interfaceId, alias);
        return allInstanceMap.get(key);
    }
}
