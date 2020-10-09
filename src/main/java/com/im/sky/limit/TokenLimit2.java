package com.im.sky.limit;

/**
 * @author jiangchangwei
 * @date 2020-7-22 下午 4:59
 **/
public class TokenLimit2 {

    public static void main(String[] args) {
        TokenLimit2 limit2 = new TokenLimit2(2, 10);
        for(int i = 0; i < 20; i++) {
            System.out.println(limit2.isLimit());
            try {
                Thread.sleep(500);
            }catch (Exception ignored) {

            }
        }
    }

    private long lastTime = System.currentTimeMillis();

    private int createTokenRate;

    private int size;

    private int tokens;

    private final Object mutex = new Object();

    public TokenLimit2(int createTokenRate, int size) {
        if(createTokenRate <= 0) {
            throw new IllegalArgumentException("createTokenRate must be positive");
        }
        if(size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }
        this.createTokenRate = createTokenRate;
        this.size = size;
        this.tokens = size;
    }

    public boolean isLimit() {
        long current = System.currentTimeMillis();
        synchronized (mutex) {
            tokens = Math.min(size, tokens + (int) (current - lastTime) / 1000 * createTokenRate);
            lastTime = current;
            if (tokens > 0) {
                tokens--;
                return false;
            } else {
                return true;
            }
        }
    }
}
