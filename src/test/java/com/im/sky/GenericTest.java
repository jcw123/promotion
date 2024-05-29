package com.im.sky;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author jiangchangwei
 * @date 2020-3-2 下午 6:14
 **/
public class GenericTest<ID> {

    private ID id;

    public static <ID> GenericTest<ID> of(String s) {
        return new GenericTest<>();
    }

    @Test
    public void test1() {
        Map<Class<?>, Consumer<?>> mm = new HashMap<>();
        mm.put(A.class, t->{});
        List<? super B> list = new ArrayList<C>();
        list.add(new B());
        list.get(0);
    }

    interface C {

    }

    class A {

    }

    class B extends A implements C {

    }
}
