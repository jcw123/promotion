package com.im.sky.datastructure.stack;

import java.util.Stack;

/**
 * @Author: jiangcw
 * @Date: 2019-7-7 下午 4:50
 * @Version 1.0
 */
public class ConversionOfNumberSystems {

    public static void main(String[] args) {
        convertDecimalToOctal(10);
    }

    /**
     * 将十进制转换为八进制
     */
    public static void convertDecimalToOctal(int N) {
        Stack<Integer> stack = new Stack<>();
        while (N != 0) {
            stack.push(N % 8);
            N /= 8;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop());
        }
        System.out.println(sb.toString());
    }
}
