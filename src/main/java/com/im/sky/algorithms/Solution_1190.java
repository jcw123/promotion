package com.im.sky.algorithms;

import java.util.LinkedList;
import java.util.Stack;

public class Solution_1190 {

    public static void main(String[] args) {
        Solution_1190 solution_1190 = new Solution_1190();
        System.out.println(solution_1190.reverseParentheses("(ed(et(oc))el)"));
    }

    public String reverseParentheses(String s) {
        if(s == null || s.length() == 0) {
            return s;
        }
        Stack<Character> stack = new Stack<>();
        LinkedList<Character> tmp = new LinkedList<>();
        stack.push(s.charAt(0));
        for(int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == ')') {
                Character c;
                while((c = stack.pop()) != '(') {
                    tmp.addLast(c);
                }
                while (!tmp.isEmpty()) {
                    stack.push(tmp.removeFirst());
                }
            }else {
                stack.push(s.charAt(i));
            }
        }
        while(!stack.isEmpty()) {
            tmp.addFirst(stack.pop());
        }
        StringBuilder sb = new StringBuilder();
        while(!tmp.isEmpty()) {
            sb.append(tmp.removeFirst());
        }
        return sb.toString();
    }
}
