package com.im.sky.javacore.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author jiangchangwei
 * @date 2020-4-7 下午 7:12
 **/
public class SelfSoftReference<T> extends SoftReference<T> {

    public SelfSoftReference(T referent) {
        super(referent);
    }

    public SelfSoftReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }
}
