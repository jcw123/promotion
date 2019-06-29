package com.im.sky.pattern.command;

/**
 * @Author: jiangcw
 * @Date: 2019-6-29 下午 7:13
 * @Version 1.0
 *
 * 命令模式：行为型模式
 *
 * 基本构件：
 * （1）抽象命令类（Command）：声明执行的方法execute()
 * （2）具体命令类（ConcreteCommand）：持有接受者对象，并在execute方法中调用
 * 接受者对象的方法
 * （3）调用者（Invoker）：即请求的发送者
 * （4）接收者（Receiver）：接收者执行与请求相关的操作，具体实现对请求的业务处理
 *
 * 命令模式功能：通过命令对象将请求者和接收者解耦
 *
 *如果命令过来，就可能需要产生过来的命令类和在接受者类中定义很多的实现方法。
 */
public class CommandBase {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new GoCommand(receiver);
        Invoker invoker = new Invoker(command);
        invoker.invoke();
    }

    interface Command<T> {
        T execute();
    }

    static class Invoker {
        private Command command;

        public Invoker(Command command) {
            this.command = command;
        }

        public void invoke() {
            Object o = command.execute();
            System.out.println(o.toString());
        }
    }

    static class GoCommand implements Command<String> {

        private Receiver receiver;

        public GoCommand(Receiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public String execute() {
            return receiver.go();
        }
    }

    static class Receiver {
        String go() {
            return "你说走咱就在，大家一起走";
        }
    }
}
