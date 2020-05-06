package com.im.sky.javacore.generic;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author jiangchangwei
 * @date 2020-4-27 下午 12:32
 **/
public class ExtendsSymbolTest {

    public static void main(String[] args) {
        Character c2 = null;
        System.out.println(null instanceof Object);
    }

    public static <A> A read(Map<A, ? extends A> map, Supplier<? extends A> supplier) {
        return map.get(supplier.get());
    }

    static class People {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "People{" +
                    "id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
            People people = (People) o;
            return id == people.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Man extends People {
        public static final String remark = "男";
    }
}
