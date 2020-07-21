package com.im.sky.javacore.datastructure;

import java.util.BitSet;

/**
 * @author jiangchangwei
 * @date 2020-7-3 下午 2:37
 **/
public class BitSetTest {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(64);
        System.out.println(bitSet.get(64));
    }
}
