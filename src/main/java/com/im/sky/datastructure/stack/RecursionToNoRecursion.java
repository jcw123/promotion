package com.im.sky.datastructure.stack;

import java.util.Stack;

/**
 * @Author: jiangcw
 * @Date: 2019-7-8 下午 12:37
 * @Version 1.0
 *
 * 对于m和n，如果m * n == 0 ，则 f = m + n +1;
 * 如果m * n ！=0 ，则 f(m,n) = f(m - 1, f(m, n - 1))
 *
 * 对于这个描述，如果采用递归的方式，两三行的代码就出来了。
 * 但是如果采用非递归的方式，需要借用栈的数据结构，同时需要理解问题的本质
 */
public class RecursionToNoRecursion {

    public static void main(String[] args) {
        //long startTime = System.currentTimeMillis();
        int f = recursion(2, 1);
        System.out.println("f：" + f);
        //System.out.println(System.currentTimeMillis() - startTime);
        //startTime = System.currentTimeMillis();
        int f1 = noRecursion(10, 1);
        //System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("f1:" +f1);
    }

    /**
     * 采用递归的方式
     * @param m
     * @param n
     * @return
     */
    public static int recursion(int m, int n) {
        if(m * n == 0) {
            return m + n + 1;
        }
        return recursion(m - 1, recursion(m, n - 1));
    }

    public static int noRecursion(int m, int n) {
        Stack<Integer> stack = new Stack<>();
        int f;
        while(true) {
            if(m * n != 0) {
                stack.push(m - 1);
                n = n - 1;
            }else {
                f = m + n +  1;
                if(stack.isEmpty()) {
                    break;
                }
                m = stack.pop();
                n = f;
            }
         }
         return f;
    }
}
