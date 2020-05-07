package com.lilin.dubboserviceprovider.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.stream.Collectors;

public class ZookeeperLock {


    /**
     * 1、每个客户端创建临时有序节点
     * 2、客户端获取节点列表，判断自己是否列表中的第一个节点，如果是就获得锁，如果不是就监听自己前面的节点，等待前面节点被删除。
     * 3、如果获取锁就进行正常的业务流程，执行完释放锁。
     * 上述步骤2中，有人可能担心如果节点发现自己不是序列最小的节点，准备添加监听器，但是这个时候前面节点正好被删除，
     * 这时候添加监听器是永远不起作用的，其实zk的API可以保证读取和添加监听器是一个原子操作。
     * 为什么要监听前一个节点而不是所有的节点呢？这是因为如果监听所有的子节点，那么任意一个子节点状态改变，
     * 其它所有子节点都会收到通知（羊群效应），而我们只希望它的后一个子节点收到通知。
     */

    private ZkClient zkClient;

    public ZookeeperLock() {
        /**
         * sessionTimeout 客户端断开连接session保存时间
         */
        zkClient = new ZkClient("127.0.0.1：2181", 5000, 20000);
    }


    /**
     * 获得锁
     *
     * @param lockId
     * @param timeOut
     * @return
     */
    public Lock lock(String lockId, long timeOut) {
        //高并发情况下面  都去zookeeper下面创建自己的节点
        Lock lockNode = createNode(lockId);
        //尝试去获取锁
        lockNode = tryActiveLock(lockNode);
        //如果没有获取锁
        if (!lockNode.isActive()) {
            try {
                synchronized (lockNode) {
                    //如果没有获取锁 那么就等待
                    lockNode.wait(timeOut);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lockNode;
    }

    /**
     * 激活锁
     *
     * @param lockNode
     * @return
     */
    public Lock tryActiveLock(Lock lockNode) {
        List<String> list = zkClient.getChildren("/lock")
                .stream()
                .sorted()
                .map(p -> "/lock" + p)
                .collect(Collectors.toList());
        if (list.get(0).equals(lockNode.getPath())) {
            //获取所有的子节点，取出第一个节点数据，如果那个节点是第一个  那么就获得锁
            lockNode.setActive(true);
        } else {
            //没有获取锁的节点  就添加监听自己的前一个节点
            String upNodePath = list.get(list.indexOf(lockNode.getPath()) - 1);
            zkClient.subscribeDataChanges(upNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                /**
                 * 如果上一个节点发生
                 * @param s
                 * @throws Exception
                 */
                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("上一个节点"+s+"删除了");
                    //尝试去获取锁
                    Lock lock = tryActiveLock(lockNode);
                    synchronized (lockNode) {
                        if (lock.isActive()) {
                            //如果获取锁的话那么就唤醒之前的wait方法
                            lockNode.notify();
                        }
                    }
                    //后面代码继续执行
                    //为了不影响程序的执行 建议删除该事件监听 监听完了就删除掉
                    zkClient.unsubscribeDataChanges(upNodePath, this);
                }
            });
        }
        return lockNode;
    }

    /**
     * 释放节点 释放锁
     */
    public void unLock(Lock lockNode){
        if(lockNode.isActive()){
            zkClient.delete(lockNode.getPath());
        }
    }

    /**
     * 创建节点
     *
     * @param lockId
     * @return
     */
    public Lock createNode(String lockId) {
        /**
         * 多个Jvm同时在Zookeeper上创建同一个相同的节点( /Lock)
         * zk节点唯一的！ 不能重复！节点类型为临时节点，
         * jvm1创建成功时候，jvm2和jvm3创建节点时候会报错，该节点已经存在。这时候 jvm2和jvm3进行等待
         * jvm1的程序现在执行完毕，执行释放锁。关闭当前会话。临时节点不复存在了并且事件通知Watcher，jvm2和jvm3继续创建。
         */
        String path = zkClient.createEphemeralSequential("/lock" + lockId, "w");
        Lock lock = new Lock();
        lock.setLockId(lockId);
        lock.setActive(false);
        lock.setPath(path);
        return lock;
    }
}
