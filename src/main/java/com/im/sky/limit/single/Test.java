package com.im.sky.limit.single;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author jiangchangwei
 * @date 2020-5-25 下午 8:15
 **/
public class Test {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:/tmp/tmp.txt"))));
        StringBuilder sb = new StringBuilder();
        String s;
        while((s = br.readLine()) != null) {
            sb.append(s);
        }
        String data = sb.toString();
        JSONObject jsonObject = JSONObject.parseObject(data);
        System.out.println(reverse(jsonObject));
    }

    private static int reverse(JSONObject jsonObject) {
        JSONArray jsonArray  = jsonObject.getJSONArray("subNodes");
        int m = 0;
        if( jsonArray != null) {
            for(int i = 0; i < jsonArray.size(); i++) {
                m = Math.max(m, reverse(jsonArray.getJSONObject(i)));
            }
        }
        return 1 + m;
    }
}

