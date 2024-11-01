package com.im.sky;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

    @Test
    public void test() {
        String s = "2024-08-19 16:15:11,355 [INFO] [JSF-BZ-22000-37-T-33] - {logId=941cce2e176943f29f313425bf05b714, CLIENT_APP_NAME=jdos_omni-order-center, CLIENT_IP=11.93.144.251, PFTID=3590022.37980.17240553109715294} - {\"businessName\":\"getOrderData\",\"businessType\":\"4\",\"consumerAppName\":\"jdos_omni-order-center\",\"consumerIp\":\"11.93.144.251\",\"dbIndex\":13,\"getFromArchiveFlag\":false,\"orderId\":295857875637,\"orderMainDataNoExistFlag\":true,\"orderXmlNoExistFlag\":false,\"providerIp\":\"11.31.122.170\",\"resultCode\":\"11003\",\"resultMessage\":\"订单数据不存在\",\"subResultCode\":\"11001\",\"tableIndex\":437,\"timestamp\":1724055311345,\"traceId\":\"3590022.37980.17240553109715294\"}";
        String regex = "(?=\\{\")\\{.*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void test2() {
        String s = "this is a test string.\nThis";
        String regex = "^This";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
//        int count = matcher.groupCount();
//        System.out.println(count);
//        boolean match = matcher.matches();
//        System.out.println(match);
//        matcher.useTransparentBounds(true);
        matcher.useTransparentBounds(true);
        boolean find = matcher.find();
        System.out.println(find);
        boolean find2 = matcher.find();
        System.out.println(find2);
//        System.out.println(matcher.start(1));
//        System.out.println(matcher.end(1));
//        boolean hitEnd = matcher.useTransparentBounds();
//        System.out.println(hitEnd);
    }

    @Test
    public void test3() {
        String input = "Hello World";
        String regex = "\\w+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 不使用透明边界，只匹配到单词边界处
        if (!matcher.find()) {
            System.out.println("No match found without transparent bounds.");
        } else {
            System.out.println("Match found at index: " + matcher.start());
        }

        // 使用透明边界，也匹配到输入字符串的开头和结尾
//        matcher.useTransparentBounds(true);
        if (matcher.find()) {
            System.out.println("Match found at index: " + matcher.start());
        }
    }
}
