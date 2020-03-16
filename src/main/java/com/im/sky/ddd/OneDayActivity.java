package com.im.sky.ddd;

/**
 * @author jiangchangwei
 * @date 2020-1-7 下午 3:54
 **/
public interface OneDayActivity {

    /**
     * 活一天，如何度过一天，对于不同的人，有不同的具体活动，但是可以通过一些细节上的抽象
     * 将一天的时间进一步细粒度化。
     */
    void liveOneDay();
}
