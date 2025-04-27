package com.im.sky.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author jiangchangwei
 * @since 2024/12/19
 */
public class JsonUtils {

    public static <T> T fromJson(String json) {
        return JSON.parseObject(json, new TypeReference<T>(){});
    }
}
