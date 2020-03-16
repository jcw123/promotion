package com.im.sky;

/**
 * @author jiangchangwei
 * @date 2020-3-2 下午 6:14
 **/
public class GenericTest<ID> {

    private ID id;

    public static <ID> GenericTest<ID> of(String s) {
        return new GenericTest<>();
    }
}
