package com.im.sky.pattern.state;

/**
 * Created by jiangchangwei on 2019/6/30.
 *
 * 命令行为模式：行为型模式
 *
 *
 * 基本构件：
 * （1）抽象状态角色(State)
 * (2)具体状态角色（ConcreteState）
 * (3)环境角色（Context）
 */
public class StateBase {

    public static void main(String[] args) {
        Context context = new Context();
        context.start();
    }

    interface State {
        void start();
    }

    abstract static class AbstractState implements State {

        protected Context context;

        protected String stateName;

        public AbstractState(String stateName, Context context) {
            this.stateName = stateName;
            this.context = context;
        }
    }

    static class GetUpSate extends AbstractState {

        public GetUpSate(String name, Context context) {
            super(name, context);
        }

        @Override
        public void start() {
            System.out.println("我起床了");
            context.setState(new TeethBrushSate(StateEnum.Teeth_BRUSH_STATE.val(), context));

        }
    }

    static class TeethBrushSate extends AbstractState {

        public TeethBrushSate(String name, Context context) {
            super(name, context);
        }

        @Override
        public void start() {
            System.out.println("我刷牙了");
            context.setState(new EatState(StateEnum.EAT_STATE.val(), context));
        }
    }

    static class EatState extends AbstractState {

        public EatState(String name, Context context) {
            super(name, context);
        }

        @Override
        public void start() {
            System.out.println("我吃饭了");
            context.setState(new GoSchoolState(StateEnum.GO_SCHOOL_STATE.val(), context));
        }
    }

    static class GoSchoolState extends AbstractState {

        public GoSchoolState(String name, Context context) {
            super(name, context);
        }

        @Override
        public void start() {
            System.out.println("我去学校了，早上的事到此结束");
        }
    }

    static class Context {
        private State state;

        public Context() {
        }

        public void setState(State state) {
            this.state = state;
            state.start();
        }

        public void start() {
            this.state = new GetUpSate(StateEnum.GET_UP_STATE.val(), this);
            this.state.start();
        }
    }

    private enum  StateEnum {
        GET_UP_STATE("GetUpState"),
        Teeth_BRUSH_STATE("TeechBrushState"),
        EAT_STATE("EatState"),
        GO_SCHOOL_STATE("GoSchoolState");

        private String name;

        private StateEnum(String name) {
            this.name  = name;
        }

        public String val() {
            return name;
        }
    }

}
