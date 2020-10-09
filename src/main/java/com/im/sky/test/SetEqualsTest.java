package com.im.sky.test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2020-8-17 下午 3:48
 **/
public class SetEqualsTest {

    public static void main(String[] args) {
        Set<Test> set = new HashSet<>();
        Test test1 = new Test();
        Test test2 = new Test();
        set.add(null);
        set.add(null);
        System.out.println(set.size());
    }

    private static class Test {
        private String systemName;

        private int age;

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return Objects.equals(systemName, test.systemName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(systemName);
        }
    }
}
