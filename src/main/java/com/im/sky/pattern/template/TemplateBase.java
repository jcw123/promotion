package com.im.sky.pattern.template;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 3:46
 * @Version 1.0
 *
 * 模板方法模式：行为型模式
 *
 * 基本构件：
 * （1）AbstractTemplate:抽象模板角色
 * （2）ConcreteTemplate：具体模板角色
 *
 * 典型应用：java中的Servlet和Mybatis中的Executor
 */
public class TemplateBase {

    public static void main(String[] args) {
    }

    static interface Servlet {
        void init();

        void service(String name);

        void destroy();
    }

    abstract static class GenericServlet implements Servlet {

        //空方法相当于钩子方法
        @Override
        public void init() {

        }

        @Override
        public void service(String name) {

        }

        @Override
        public void destroy() {

        }
    }

    /**
     * 还是相当于抽象模板类
     */
    static class HttpServlet extends GenericServlet {

        /**
         * 这个方法就是一个模板，按照这个模板调用，只是其中
         * 调用的具体方法可以在子类中重新实现
         * @param name
         */
        @Override
        public void service(String name) {
            if("GET".equals(name)) {
                doGet();
            }else {
                doPost();
            }
        }

        public void doGet() {
            throw new UnsupportedOperationException();
        }

        public void doPost() {
            throw new UnsupportedOperationException();
        }
    }

    static class InitServlet extends HttpServlet {
        @Override
        public void doGet() {
            System.out.println("invoke doGet method");
        }

        @Override
        public void doPost() {
            System.out.println("invoke doPost method");
        }
    }
}
