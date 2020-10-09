package com.im.sky.generator;

/**
 * @author jiangchangwei
 * @date 2020-8-12 下午 5:36
 **/
public class AbstractGenerator implements Generator {
    @Override
    public Long id() {
        throw new UnsupportedOperationException("no support for generating id use long type");
    }

    @Override
    public String idStr() {
        throw new UnsupportedOperationException("no support for generating id use string type");
    }
}
