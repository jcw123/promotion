package com.im.sky.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.io.*;
import java.util.*;

/**
 * @Author: jiangcw
 * @Date: 2019-10-16 下午 3:50
 * @Version 1.0
 */
public class DBParseUtils {

    public static void main(String[] args) throws Exception {
        String masterIpFileName = "d:/tmp/masterIp.txt";
        String allIpFileName = "d:/tmp/slaveIp.txt";
        File masterFile = new File(masterIpFileName);
        BufferedReader masterBr = new BufferedReader(new InputStreamReader(new FileInputStream(masterFile)));
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allIpFileName), true));
        String masterIp;
        ArrayList<String> masterIpList = new ArrayList<>();
        ArrayList<String> slaveIpList = new ArrayList<>();
        String url = "http://dbsv4.jd.com/dbmanage/queryMasterSlaveArch/search";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "__jdu=1567672074049478626477; shshshfpa=f878033c-b1e6-7114-9472-2096e99bf86c-1567684037; pinId=ZNjB0Aag1-LDdGk6hlfTM7V9-x-f3wj7; pin=393566149-105788; unick=%E9%9A%8F%E9%A3%8Eemmm; _tp=UrfmDMxYbuV4PNj9z7hGNXWCg9deUQOt491vB3%2F5xCw%3D; _pst=393566149-105788; shshshfpb=rtItIhZ%20AiWsl2AklXgOZNQ%3D%3D; ipLocation=%u5317%u4eac; cn=39; __jdv=122270672|www.jd.com|t_0_|zssc|802871cc-0ee0-450c-90ba-7974f385a0df-p_132524|1570018568929; areaId=1; ipLoc-djd=1-2809-51216-0; PCSYCityID=CN_110000_110100_110112; user-key=4226cb03-caba-4561-a92c-807dbfc0bf72; TrackID=1nogJiZDI4hLRSclLXLgJj2UdE2AQofnGS4sJk36gKtTN6W3kRvJO-3wRCIvQruWqnqdHlHqI9oP3YtaVstJwMvyT2LLNuNZX9Z0JsvXaZk4; sso.jd.com=BJ.a0c3c69a05bc4c54be9c7d1fa077cbdf; shshshfp=f7db5f04189424cc5bb5cad4023fd2a7; jd.erp.lang=zh_CN; 3AB9D23F7A4B3C9B=4C5V5YTO5DT2V6BUAZXBPAPBS2FO24KLQ2H6C2SR4LUMMXOUERRRVYB5PENYPFLXKYDE7NBHBJMJ6EG5BSN6BPZITA; JSESSIONID=9602500EAAC4B62114EC40BB3DB46675.s1; __jdc=62267774; __jda=62267774.1567672074049478626477.1567672074.1571206029.1571209102.168");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        while((masterIp = masterBr.readLine()) != null) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("ip", masterIp)
                    .add("port", "3358")
                    .add("needDomain", "true")
                    .build();
            String data = HttpUtils.post(url, headers, requestBody);
            parseBody(data, masterIpList, slaveIpList);
        }
        System.out.println(JSON.toJSONString(slaveIpList));

    }

    private static void parseBody(String body, List<String> masterIpList, List<String> slaveIpList) {
        if(body != null) {
            JSONObject jsonObject = JSON.parseObject(body);
            Optional.ofNullable(jsonObject.getJSONObject("data"))
                    .map(t -> t.getJSONArray("nodes"))
                    .map(t -> {
                        for(int i = 0; i < t.size(); i++) {
                            JSONObject jsonObject1 = t.getJSONObject(i);
                            String ip = jsonObject1.getString("ip");
                            String masterIp = jsonObject1.getString("masterIp");
                            if(masterIp == null || masterIp.equals("")) {
                                masterIpList.add(ip);
                            }else {
                                slaveIpList.add(ip);
                            }
                        }
                        return null;
                    });
        }
    }
}
