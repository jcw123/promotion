package com.im.sky.netty.test.test2.protocol;

import io.netty.buffer.ByteBuf;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:21
 **/
public interface Protocol {

    Object decode(ByteBuf data, Class clazz);

    Object decode(ByteBuf data, String classTypeName);

    ByteBuf encode(Object obj, ByteBuf buf);
}
