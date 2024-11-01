package com.im.sky.web;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
public class MyApplication {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/test", produces = "application/json;charset=UTF-8" )
    Object test(HttpServletRequest request) {
        Map<String, String[]> paramsMap = request.getParameterMap();
        Enumeration<String> headers = request.getHeaderNames();
        System.out.println("start header");
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            System.out.println("key:" + key + ", value:" + request.getHeader(key));
        }
        System.out.println("end header");
        System.out.println(JSON.toJSONString(paramsMap));
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        return map;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyApplication.class);
        app.run(args);
    }

}
