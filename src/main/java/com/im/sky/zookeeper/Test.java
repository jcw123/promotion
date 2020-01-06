package com.im.sky.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * @author jiangchangwei
 * @date 2019-12-8 下午 5:48
 **/
public class Test {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.99.100:2182", new RetryNTimes(10, 5000));
        client.start();
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                System.out.println("监控:" + watchedEvent);
            }
        }).forPath("/");
        System.out.println(JSON.toJSONString(children));
        String result = client.create().withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/test", "testData".getBytes("utf-8"));
        System.out.println("result:" + result);
        client.setData().forPath("/test", "111".getBytes());
        client.setData().forPath("/test", "222".getBytes());
        System.out.println(client.checkExists().forPath("/test"));
        client.delete().withVersion(-1).forPath("/test");
        System.out.println(client.checkExists().forPath("/test"));
        client.close();
        System.out.println("OK!");
    }
}
