package com.im.sky.datastructure.stack;

import java.util.Stack;

/**
 * @Author: jiangcw
 * @Date: 2019-7-7 下午 6:30
 * @Version 1.0
 * 表达式求值
 *
 * 利用栈结构进行表达式求值
 *
 * 表达式的形式:中序表达式、后序表达式、前序表达式
 *
 * 中序表达式:
 * 优点：方便阅读和理解，符合人的思维习惯
 * 缺点：存在算符优先和子表达式优先的问题
 *
 * 如果表达式是后序的形式，通过程序计算非常方便
 */
public class ExpressionCalculate {

    public static void main(String[] args) {
        System.out.println(buildPostExpression("3*(4-2*8+(5-3))#"));
        System.out.println(calculateByPostExpression(buildPostExpression("3*(4-2*8+(5-3))#")));
    }

    /**
     * 通过中序表达式构造后序表达式，中序表达式以“#”结尾
     * @param middleExpression
     * @return
     */
    public static String buildPostExpression(String middleExpression) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        char[] cArray = middleExpression.toCharArray();
        for(char c : cArray) {
            if(c == '#') {
                while(!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                break;
            }
            if(c >= '0' && c <= '9') {
                sb.append(c);
            }else if(c == ')') {
                while(stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                stack.pop();
            }else {
                while(!stack.isEmpty() && compare(stack.peek(), c)) {
                    sb.append(stack.pop());
                }
                stack.push(c);
            }
        }
        return sb.toString();
    }

    public static int calculateByPostExpression(String postExpression) {
        Stack<String> stack = new Stack<>();
        char[] cArray = postExpression.toCharArray();
        for(char c : cArray) {
            if(c >= '0' && c <= '9') {
                stack.push(c + "");
            }else {
                String b = stack.pop();
                String a = stack.pop();
                stack.push(String.valueOf(eval(a, b, c)));

            }
        }
        return Integer.valueOf(stack.pop());
    }

    private static int eval(String a, String b, char operator) {
        int aVal = Integer.valueOf(a);
        int bVal = Integer.valueOf(b);
        switch(operator) {
            case '+':
                return aVal + bVal;
            case '-':
                return aVal - bVal;
            case '*':
                return aVal * bVal;
            case '/':
                return aVal / bVal;
            default:
                return 1;
        }
    }

    /**
     * 根据运算符规则，如果c1 >= c2，则返回true，否则返回false。
     * 这里的优先级比较时最重要的，这部分逻辑抽象不出来，算法肯定写不出来
     * @param c1
     * @param c2
     * @return
     */
    private static boolean compare(char c1, char c2) {
        int c1Change;
        int c2Change;
        switch (c1) {
            case '(':
                c1Change = 0;
                break;
            case '+':
                c1Change = 1;
                break;
            case '-':
                c1Change = 1;
                break;
            case '*':
                c1Change = 2;
                break;
            case '/':
                c1Change = 2;
                break;
            default:
                c1Change = -1;
        }
        switch(c2) {
            case '+':
                c2Change = 1;
                break;
            case '-':
                c2Change = 1;
                break;
            case '*':
                c2Change = 2;
                break;
            case '/':
                c2Change = 2;
                break;
            case '(':
                c2Change = 3;
                break;
            default:
                c2Change = -1;
        }
        return c1Change >= c2Change;

    }
}
