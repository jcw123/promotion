package com.im.sky.pattern.responsibility;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 7:45
 * @Version 1.0
 *
 * 责任链模式：行为型模式
 *
 * 基本构件：
 * （1）Handler（抽象处理者）
 * （2）ConcreteHandler（具体处理者）
 *
 * 纯的责任链模式：一个处理器要么承担责任，要么把责任推给下一家，不能
 * 承担了一部分责任后然后把责任继续推给下一家。但在使用过程中基本是
 * 使用不纯的责任链模式
 *
 * 典型应用：Tomcat中的Filter；Spring MVC中的Intercepter
 *
 * tomcat中并不是通过一个filter中有下一个filter的引用来保存关系的，即不是一个
 * 链式数据结构，而是在ApplicationFilterChain中的一个数组来存放所有的过滤器的
 *
 * Warning：建链时需要注意，防止陷入死循环
 *
 *
 */
public class ResponsibilityBase {

    public static void main(String[] args) {
        ServletRequest request = new HttpServletRequest(1);
        ServletResponse response = new HttpServletResponse();
        Filter filter = new AFilter();
        Filter filter1 = new BFilter();
        filter.setNextFilter(filter1);
        ApplicationFilterChain chain = new ApplicationFilterChain(filter);
        chain.doFilter(request, response);

    }

    static interface Filter {
        void doFilter(ServletRequest request, ServletResponse response);

        void setNextFilter(Filter filter);

        Filter getNextFilter();
    }

    static class AFilter implements Filter {

        private Filter nextFilter;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            if(request.getValue() < 2) {
                System.out.println("被AFilter拦截住了");
            }else {
                if(nextFilter != null) {
                    nextFilter.doFilter(request, response);
                }
            }
        }

        @Override
        public void setNextFilter(Filter filter) {
            this.nextFilter = filter;
        }

        @Override
        public Filter getNextFilter() {
            return nextFilter;
        }
    }

    static class BFilter implements Filter {

        private Filter nextFilter;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            if(request.getValue() < 10) {
                System.out.println("被BFilter拦截住了");
            }else {
                if(nextFilter != null) {
                    nextFilter.doFilter(request, response);
                }
            }
        }

        @Override
        public void setNextFilter(Filter filter) {
            this.nextFilter = filter;
        }

        @Override
        public Filter getNextFilter() {
            return nextFilter;
        }
    }

    static interface FilterChain {
        void doFilter(ServletRequest request, ServletResponse response);
    }

    static class ApplicationFilterChain implements FilterChain {

        private Filter root;

        public ApplicationFilterChain(Filter filter) {
            this.root = filter;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            root.doFilter(request, response);
        }
    }



    static interface ServletRequest {
        int getValue();
    }

    static interface ServletResponse {

    }

    static class HttpServletRequest implements ServletRequest {

        private int re;

        public HttpServletRequest(int re) {
            this.re = re;
        }

        @Override
        public int getValue() {
            return re;
        }
    }

    static class HttpServletResponse implements ServletResponse {

    }
}
