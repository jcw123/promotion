package com.im.sky.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author jiangchangwei
 * @date 2020-1-8 下午 3:16
 **/
public class StreamTest {

    public static void main(String[] args) throws InterruptedException {
        while(true) {
            test1();
            Thread.sleep(200);
        }
    }

    private static void test1() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4));
        Stream<Integer> stream = list.stream().parallel()
                .onClose(()-> System.out.println("流关闭了"));
        System.out.println(stream.findAny().orElse(-1));
    }
}
