package com.im.sky.ddd;

/**
 * @author jiangchangwei
 * @date 2020-1-7 下午 3:57
 **/
public abstract class AbstractOneDayActivity implements OneDayActivity {

    /**
     * 一天开始的初始化
     */
    abstract void init();

    /**
     * 一天的正式开始
     */
     private void start() {
         _start();
     }

    protected abstract void _start();

    protected abstract void _end();

    /**
     * 一天的正式结束
     */
    private void end() {
        _end();
    }

    protected void doOtherSomething() {
        brushTeeth();
        eat();
        doMorningActivities();
        eat();
        doAfternoonActivities();
        eat();
        doNightActivities();
        sleep();

    }

    protected void brushTeeth() {
        System.out.println("刷牙");
    }

    protected void eat() {
        System.out.println("吃饭");
    }

    protected void sleep() {
        System.out.println("睡觉啦");
    }

    abstract void doMorningActivities();

    abstract void doAfternoonActivities();

    abstract void doNightActivities();

    @Override
    public void liveOneDay() {
        init();
        start();
        doOtherSomething();
        end();
    }
}
