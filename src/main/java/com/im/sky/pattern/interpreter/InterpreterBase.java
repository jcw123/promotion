package com.im.sky.pattern.interpreter;

import java.util.Stack;

/**
 * Created by jiangchangwei on 2019/6/30
 *
 * 解释器模式：行为型模式
 *
 * 基本构件：
 * （1）AbstractExpression（抽象表达式）：声明解释器中抽象的操作
 * （2）TerminalExpression(终结符表达式)：在表达式中叶子节点就相当于终结符表达式的实例
 * （3）NonTerminalExpression（非终结符表达式）：非叶子节点相当于非终结符表达式的实例
 * 终结符表达式通过非终结符表达式连接在一起
 *
 * 典型应用：spring中的EL表达式
 *
 * 注：表达式最终通过词法分析、语法分析最终会构成一个抽象语法树，基于这颗树进行语义分析，最终解释执行。
 * 这种设计模式不常用
 *
 *
 * 适用场景：
 * （1）需要将一个解释执行的语句表示成一个抽象语法树
 * （2）重复出现的问题可以用一些语法表示
 * （3）语言的文法较为简单
 * （4）对执行效率要求不高
 */
public class InterpreterBase {

    public static void main(String[] args) {
        CalContext calContext = new CalContext("1 + 2 + 3");
        System.out.println(calContext.calculate());
    }

    interface ArithmeticExpression {
        int interpret();
    }

    static class NumberExpression implements ArithmeticExpression {

        private int num;

        public NumberExpression(int num) {
            this.num = num;
        }

        @Override
        public int interpret() {
            return num;
        }
    }

    abstract static class OperatorExpression implements ArithmeticExpression {
        protected ArithmeticExpression expression1;

        protected ArithmeticExpression expression2;

        public OperatorExpression(ArithmeticExpression expression1, ArithmeticExpression expression2) {
            this.expression1 = expression1;
            this.expression2 = expression2;
        }
    }

    static class AddExpression extends OperatorExpression {

        public AddExpression(ArithmeticExpression expression1, ArithmeticExpression expression2) {
            super(expression1, expression2);
        }

        @Override
        public int interpret() {
            return expression1.interpret() + expression2.interpret();
        }
    }

    static class CalContext {
        private Stack<ArithmeticExpression> stack = new Stack<>();

        public CalContext(String expression) {
            String[] elements = expression.split(" ");
            ArithmeticExpression e1;
            ArithmeticExpression e2;
            for(int i = 0 ; i < elements.length; i++) {
                switch (elements[i].charAt(0)) {
                    case '+':
                        e1 = stack.pop();
                        e2 = new NumberExpression(Integer.valueOf(elements[++i]));
                        stack.push(new AddExpression(e1, e2));
                        break;
                    default:
                        stack.push(new NumberExpression(Integer.valueOf(elements[i])));
                }
            }
        }

        public int calculate() {
            return stack.pop().interpret();
        }
    }
}
