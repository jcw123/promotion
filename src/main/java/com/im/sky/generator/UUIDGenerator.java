package com.im.sky.generator;

import java.util.UUID;

/**
 * @author jiangchangwei
 * @date 2020-8-10 下午 9:03
 **/
public class UUIDGenerator extends AbstractGenerator {

    @Override
    public String idStr() {
        return UUID.randomUUID().toString();
    }
}
