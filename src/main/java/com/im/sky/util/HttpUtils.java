package com.im.sky.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: jiangcw
 * @Date: 2019-10-16 下午 4:08
 * @Version 1.0
 */
public class HttpUtils {

    public static void main(String[] args) throws InterruptedException, IOException {}

    private static final Log log = LogFactory.getLog(HttpUtils.class);

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> header) {
        return get(url, header, null);
    }

    public static String get(String url, Map<String, String> header, Map<String, String> params) {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        StringBuilder sb = new StringBuilder();
        if(params != null && params.size() > 0) {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url += "?" + sb.deleteCharAt(sb.length() - 1);
        }
        builder.url(url);
        if(header != null && header.size() > 0) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();
        try {
            Response response = httpClient.newCall(request).execute();
            ResponseBody body = response.body();
            if(body != null) {
                return body.string();
            }
        }catch (IOException e) {
            log.error("http get error, url:" + url);
        }
        return null;
    }

    public static String post(String url, Map<String, String> header, RequestBody requestBody) {
        OkHttpClient httpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(header != null && header.size() > 0) {
            for(Map.Entry<String, String> entry : header.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if(responseBody != null) {
                return responseBody.string();
            }
        }catch (IOException e) {
            log.error("http post error, url:" + url);
        }
        return null;
    }
}
