package com.im.sky.datastructure.queue.test;

import com.im.sky.datastructure.queue.BaseEle;
import com.im.sky.datastructure.queue.MultiPriorityQueue;

/**
 * @author jiangchangwei
 * @date 2020-5-13 下午 8:38
 **/
public class MultiPriorityQueueTest {

    public static void main(String[] args) {
        MultiPriorityQueue<BaseEle> queue = new MultiPriorityQueue<>(3, 10);
        BaseEle ele1 = new BaseEle(10);
        BaseEle ele2 = new BaseEle(10);
        queue.add(ele1);
        queue.add(ele2);
        System.out.println(queue.poll().index());

    }
}
