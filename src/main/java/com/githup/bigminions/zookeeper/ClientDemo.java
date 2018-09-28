package com.githup.bigminions.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by daren on 2018/8/28.
 * Pre : install zookeeper in your local machine.
 */
public class ClientDemo {

    public static void main(String[] args) throws InterruptedException {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        String path = "/test";
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "testData".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(60 * 1000);

        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }
}
