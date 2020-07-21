package com.im.sky.netty.test.test2.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 2:47
 **/
public class PooledBufHolder {

    private static final ByteBufAllocator POOLED = new UnpooledByteBufAllocator(false);

    public static ByteBufAllocator getInstance() {
        return POOLED;
    }

    public static ByteBuf getBuffer() {
        return POOLED.buffer();
    }

    public static ByteBuf getBuffer(int size) {
        return POOLED.buffer(size);
    }
}
