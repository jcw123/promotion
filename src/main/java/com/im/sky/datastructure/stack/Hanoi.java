package com.im.sky.datastructure.stack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * @Author: jiangcw
 * @Date: 2019-7-7 下午 8:33
 * @Version 1.0
 *
 * Hanni塔问题
 *
 * 问题描述：有A、B、C 3个塔，n个直径不同的牌子依次从小到大编号为1,2,...n放在A塔上。
 * 将A塔的盘子移到C塔，一次只能移动一个盘子，且任何时候不能出现大的盘子在小的盘子之上。
 *
 * 汉诺塔问题的时间复杂度非常高，如果不通过程序实现非常耗时，但是呢，如果通过程序实现的
 * 话因为汉诺塔问题具有规律性，所以写起代码来非常方便。
 */
public class Hanoi {

    public static void main(String[] args) throws Exception {
        Stack<Integer> A = new Stack<>();
        Stack<Integer> B = new Stack<>();
        Stack<Integer> C = new Stack<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());
        for(int i = n ; i >= 1; i--) {
            A.push(i);
        }
        hanoiStart(n, A, B, C);
        System.out.println(C.toString());
    }

    public static void hanoiStart(int n, Stack<Integer> A, Stack<Integer> B, Stack<Integer> C) {
        if(n == 1) {
            C.push(A.pop());
            return;
        }
        hanoiStart(n - 1, A, C, B);
        C.push(A.pop());
        hanoiStart(n - 1, B, A, C);
    }
}
