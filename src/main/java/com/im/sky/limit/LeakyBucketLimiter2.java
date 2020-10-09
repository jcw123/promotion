package com.im.sky.limit;

/**
 * @author jiangchangwei
 * @date 2020-7-22 下午 12:58
 **/
public class LeakyBucketLimiter2 {

    public static void main(String[] args) {
        LeakyBucketLimiter2 limiter2 = new LeakyBucketLimiter2(10, 2);
        for(int i = 0; i < 20; i++) {
            System.out.println(limiter2.isLimit());
            try {
                Thread.sleep(1000);
            }catch (Exception ignored) {

            }
        }
    }

    private final int capacity;

    private int water;

    private final int rate;

    private volatile long lastTime = System.currentTimeMillis();

    private final Object mutex = new Object();

    public LeakyBucketLimiter2(int capacity, int rate) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        if(rate <= 0) {
            throw new IllegalArgumentException("rate must be positive");
        }
        this.capacity = capacity;
        this.rate = rate;
    }

    public boolean isLimit() {
        long current = System.currentTimeMillis();
        long flowCount = (current - lastTime) / 1000 * rate;
        synchronized (mutex) {
            water = Math.max(0, (int) (water - flowCount));
            lastTime = current;
            if (water < capacity) {
                water++;
                return false;
            } else {
                return true;
            }
        }
    }
}
