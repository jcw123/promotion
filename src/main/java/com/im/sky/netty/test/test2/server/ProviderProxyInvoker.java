package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.config.ProviderConfig;
import com.im.sky.netty.test.test2.filter.FilterChain;
import com.im.sky.netty.test.test2.filter.ProviderInvokerFilter;
import com.im.sky.netty.test.test2.msg.BaseMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 5:51
 **/
@Slf4j
public class ProviderProxyInvoker implements Invoker {

    private final ProviderConfig providerConfig;

    private final FilterChain filterChain;

    public ProviderProxyInvoker(ProviderConfig config) {
        this.providerConfig =  config;
        this.filterChain = FilterChain.buildProviderChain(new ProviderInvokerFilter(providerConfig), providerConfig);
    }

    @Override
    public ResponseMessage invoke(BaseMessage msg) {
        return null;
    }
}
