package com.im.sky.datastructure.queue;

import java.util.Random;

/**
 * @author jiangchangwei
 * @date 2020-5-13 下午 8:39
 **/

public class BaseEle implements IndexSelect {

    private Random random;

    private int maxPriority;

    public BaseEle(int maxPriority) {
        this.maxPriority = maxPriority;
        random = new Random();
    }

    @Override
    public int index() {
        return random.nextInt(maxPriority);
    }
}
