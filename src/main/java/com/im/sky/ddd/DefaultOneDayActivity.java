package com.im.sky.ddd;

/**
 * @author jiangchangwei
 * @date 2020-1-7 下午 4:08
 **/
public class DefaultOneDayActivity extends AbstractOneDayActivity {

    public static void main(String[] args) {
        OneDayActivity oneDayActivity = new DefaultOneDayActivity();
        oneDayActivity.liveOneDay();
    }

    @Override
    void init() {
        System.out.println("酝酿一下开始。。");
    }

    @Override
    protected void _start() {
        System.out.println("闹钟响了，start");
    }

    @Override
    protected void _end() {
        System.out.println("一天正式结束了, end");
    }

    @Override
    void doMorningActivities() {
        System.out.println("发呆一上午");
    }

    @Override
    void doAfternoonActivities() {
        System.out.println("发呆一下午");
    }

    @Override
    void doNightActivities() {
        System.out.println("发呆一晚上");
    }
}
