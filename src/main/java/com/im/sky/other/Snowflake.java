package com.im.sky.other;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author jiangchangwei
 * @date 2019-12-9 下午 12:49
 *
 * 雪花算法的实现
 *
 *  实现原理：
 *  从最高位开始:
 *  1bit：固定为0
 *  41bit：时间戳，单位是毫秒
 *  10bit：5bit表示机房id，5bit表示机器id
 *  12bit：用来记录同一毫秒之类产生的不同id
 *
 *  根据雪花算法来实现生产者和消费者模式进行数据消费，对于具体如何设置机器的数目，需要考虑
 *  消费者的消费能力，可以用一个监控线程来观察队列大小的变化，如果能够保证基本每次监控时
 *  队列都基本是满的，那么生产者的生产速度是可以满足消费者的。
 **/
public class Snowflake {

    private static BlockingQueue<Long> queue = new ArrayBlockingQueue<>(10240);

    /**
     * 数据中心id
     */
    private long dataCenterId;

    /**
     * 机器id
     */
    private long workerId;

    /**
     * 序列号
     */
    private long sequence;

    private long epoch;

    private long workerIdBits = 5;

    private long dataCenterIdBits = 5;

    private long maxWorkerId = ~(-1L << workerIdBits);

    private long maxDataCenterId = ~(-1L << dataCenterIdBits);

    private long sequenceBits = 12;

    private long workerIdShift = workerIdBits;

    private long dataCenterShift = dataCenterIdBits;

    private long timestampShift = sequenceBits + workerIdBits + dataCenterIdBits;

    private long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public Snowflake(long dataCenterId, long workerId) {
        if(dataCenterId < 0 || dataCenterId > maxDataCenterId) {
            throw new IllegalArgumentException("illegal dataCenterId");
        }
        if(workerId < 0  || workerId > maxWorkerId) {
            throw new IllegalArgumentException("illegal workerId");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        this.epoch = System.currentTimeMillis() & (~(-1L << 41));
    }


    private long timeGen() {
        return System.currentTimeMillis();
    }

    private long tillNextMills(long lastTimestamp) {
        long timeStamp = timeGen();
        while(timeStamp <= lastTimestamp) {
            timeStamp = timeGen();
        }
        return timeStamp;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if(timestamp < lastTimestamp) {
            throw new RuntimeException("Clock move backward, refusing to generate id for " + (lastTimestamp - timestamp) +  "milliseconds");
        }
        // 这里是保证不重复的核心
        if(lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if(sequence == 0) {
                timestamp = tillNextMills(lastTimestamp);
            }
        }else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - epoch) << timestampShift) |
                (dataCenterId << dataCenterShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 1; i++) {
            Snowflake snow = new Snowflake(0, i);
            new Thread(() -> {
                while (true) {
                    try {
                        queue.put(snow.nextId());
                        Thread.sleep(50);
                    }catch (Exception e) {}
                }
            }, "producer").start();
        }
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                while(true) {
                    try {
                        long start = System.currentTimeMillis();
                        queue.take();
                        System.out.println(System.currentTimeMillis() - start);
                    }catch (Exception e) {}
                }
            }, "consumer").start();
        }

//        new Thread(() -> {
//            while(true) {
//                try {
//                    System.out.println(queue.size());
//                    Thread.sleep(1000);
//                }catch (Exception e) {}
//            }
//        }, "monitor").start();
    }


}
