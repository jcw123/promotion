package com.im.sky;

import org.junit.Test;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-04-08 11:12
 **/
public class ExtentdsTest {

    interface A {

        B toDTO();

    }

    interface B {

    }

    abstract class C implements A, B {
        @Override
        public B toDTO() {
            return this;
        }
    }

    class D extends  C {
        @Override
        public B toDTO() {
            return this;
        }
    }

    @Test
    public void testInstanceOf() {
        B b = new D().toDTO();
        System.out.println(b instanceof C);
    }
}
