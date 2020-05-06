package com.im.sky.javacore.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 7:49
 *
 * 当队列中有数据后，相当于对应的数据移除了
 **/
public class PhantomReferenceTest {
    public static void main(String[] args) {
        ReferenceQueue<People> referenceQueue = new ReferenceQueue<>();
        PhantomReference<People> reference = new PhantomReference<>(new People(), referenceQueue);
        System.out.println(reference.get());
        System.gc();
        Reference<? extends People> reference1;
        while(true) {
            if((reference1 = referenceQueue.poll()) != null) {
                System.out.println("data:" + reference1.get());
            }
            try {
                Thread.sleep(1000);
                System.gc();
            }catch (Exception ignored) {}
        }
    }
}
