package com.im.sky.collection;

import java.util.BitSet;

/**
 * @author jiangchangwei
 * @date 2020-1-15 上午 10:59
 **/
public class BitSetTest {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(2);
        System.out.println(bitSet.get(2));
        System.out.println(bitSet.get(1));
    }
}
