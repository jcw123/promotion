package com.im.sky;

import lombok.Data;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-06-12 15:52
 **/
public class GCTest {

    @Test
    public void tesgGC() {
        Map<WeakReference<User>, WeakReference<User>> map = new HashMap<>();
        ReferenceQueue<User> queue = new ReferenceQueue<>();
        for(int i = 0; i < 10000; i++) {
            WeakReference<User> reference = new WeakReference<>(new User(), queue);
            map.put(reference, reference);
        }
    }

    @Data
    private static class User {
        byte[] bytes = new byte[1 * 1024 * 1024];
    }

    @Test
    public void testPhantomReference() throws Exception {
        String s = new String("abc");
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> reference = new PhantomReference<>(s, queue);
        s = null;
        Thread.sleep(5 * 1000);
        System.gc();
        Thread.sleep(5 * 1000);
        while(true) {
            Object o = queue.poll();
            if(o != null) {
                System.out.println(o);
            }
        }
    }

    @Test
    public void testWeakReference() throws Exception {
        String s = new String("abc");
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        WeakReference<String> reference = new WeakReference<>(s, queue);
        System.out.println(reference.get());
        s = null;
        System.out.println(reference.get());
        Thread.sleep(5 * 1000);
        System.gc();
        System.out.println(reference.get());
        Thread.sleep(5 * 1000);
        System.out.println(reference.get());
        while(true) {
            Object o = queue.poll();
            if(o != null) {
                System.out.println(o);
                break;
            }
        }

    }
}
