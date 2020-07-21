package com.im.sky.netty.test.test2.filter;

import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 3:31
 **/
public interface Filter {

    ResponseMessage invoke(RequestMessage request);
}
