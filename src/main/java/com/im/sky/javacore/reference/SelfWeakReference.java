package com.im.sky.javacore.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 4:46
 **/
public class SelfWeakReference<T> extends WeakReference<T> {

    public SelfWeakReference(T referent) {
        super(referent);
    }

    public SelfWeakReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }
}
