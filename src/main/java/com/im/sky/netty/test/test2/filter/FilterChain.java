package com.im.sky.netty.test.test2.filter;

import com.im.sky.netty.test.test2.config.ConsumerConfig;
import com.im.sky.netty.test.test2.config.ProviderConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 3:41
 **/
@Slf4j
public class FilterChain {

    private Filter chain;

    private Map<String, Object> context;

    private FilterChain(List<AbstractFilter> filters, Filter lastFilter, Map<String, Object> context) {
         chain = lastFilter;
         this.context = context;
         if(filters != null && filters.size() > 0) {
             AbstractFilter filter;
             for(int i = filters.size()  - 1;  i >= 0; i--) {
                 filter = filters.get(i);
                 filter.setNext(chain);
                 filter.setConfigContext(context);
                 chain = filter;
             }
         }
    }

    public static FilterChain buildProviderChain(Filter filter, ProviderConfig config) {
        Map<String, Object> context = config.getContext();
        List<AbstractFilter> filters = config.getFilters();
        return new FilterChain(filters, filter, context);
    }

    public static FilterChain buildConsumerChain(Filter filter, ConsumerConfig config) {
        Map<String, Object> context = config.getContext();
        List<AbstractFilter> filters = config.getFilters();
        return new FilterChain(filters, filter, context);
    }


}
