package com.im.sky.stocks;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Random;

/**
 * @description 股票预测，何必那么复杂
 * @date 2023-07-25 15:19
 **/
public class StocksPredicate {

    static Random random = new Random();

    public static void main(String[] args) {
        if(predicate()) {
            System.out.println("明天股市大涨");
        }else {
            System.out.println("明天股市大跌");
        }
    }

    // 基于当前时间进行10万次奇偶性判断，如果偶数次数大于奇数次数，表示明天股市大涨
    private static boolean predicate() {
        long max = 100000;
        LocalDateTime localDate = LocalDateTime.now();
        LocalDateTime nextDayTime = localDate.plusDays(1);
        long timestamp = nextDayTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        int i = 0;
        int count = 0;
        while (i < max) {
            timestamp += random.nextInt(100);
            if((timestamp & 1) == 0) {
                count++;
            }
            i++;
        }
        return count * 2 > max;
    }
}
