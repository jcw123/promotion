package com.im.sky.netty.test.test2.config;

import com.im.sky.netty.test.test2.filter.AbstractFilter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 4:05
 **/
public class ProviderConfig<T> extends AbstractIdConfig implements Serializable {

    private List<AbstractFilter> filters;

    private Map<String, Object> context;

    /**
     * provider实现者的真正引用
     */
    private T ref;

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public List<AbstractFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<AbstractFilter> filters) {
        this.filters = filters;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }
}
