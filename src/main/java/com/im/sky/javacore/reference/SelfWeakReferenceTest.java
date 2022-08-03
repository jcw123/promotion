package com.im.sky.javacore.reference;

import java.lang.ref.ReferenceQueue;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 5:43
 **/
public class SelfWeakReferenceTest {

    public static void main(String[] args) throws Exception {
        ReferenceQueue<People> referenceQueue = new ReferenceQueue<>();
        People people = new People();
        SelfWeakReference<People> weakReference = new SelfWeakReference<>(people, referenceQueue);
        SelfWeakReference<People> weakReference1 = new SelfWeakReference<>(new People(), referenceQueue);
        System.out.println(weakReference.get());
        System.out.println(weakReference1.get());
        System.out.println(referenceQueue.poll());
        System.out.println(referenceQueue.poll());
        people = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println(weakReference.get());
        System.out.println(weakReference1.get());
        System.out.println(referenceQueue.poll());
        System.out.println(referenceQueue.poll());
    }
}
