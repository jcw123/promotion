package com.im.sky.netty.zs.protocol;

import io.netty.buffer.ByteBuf;

public interface Protocol {

    /**
     * decode by Class Object
     * @param data
     * @param cls
     * @return
     */
    Object decode(ByteBuf data, Class cls);

    /**
     * decode by class type name
     * @param data
     * @param clsTypeName
     * @return
     */
    Object decode(ByteBuf data, String clsTypeName);

    /**
     * encode obj
     * @param obj
     * @param buf
     * @return
     */
    ByteBuf encode(Object obj, ByteBuf buf);
}
