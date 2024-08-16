package com.im.sky;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-05-31 17:26
 **/
public class DateTest {

    @Test
    public void testZoneId() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(JSON.toJSONString(zoneIds));
    }

    @Test
    public void testPeriod() {
        LocalDate startTime = LocalDate.of(2024, 5, 5);
        LocalDate endTime = LocalDate.of(2024, 6, 7);
        Period period = Period.between(endTime, startTime);
        System.out.println(period.getDays());
        LocalDateTime localDateTime = LocalDateTime.now();
        String s = localDateTime.toInstant(ZoneOffset.ofHours(8)).toString();
        System.out.println(s);
    }

    @Test
    public void testInstant() {
        Long time = System.currentTimeMillis();
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void testZoneId2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String s= localDateTime.atZone(ZoneId.of("Asia/Shanghai"))
                .withZoneSameInstant(ZoneId.of("America/New_York"))
                .toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(s);
    }

    @Test
    public void testZoneOffset() {
        System.out.println(LocalDateTime.now().atOffset(ZoneOffset.ofHours(9)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        aa(list);
        System.out.println(list.size());
    }

    private void aa(List<Integer> list) {
        list = list.subList(0, 1);
//        list.remove(1);
    }

    @Test
    public void test2() throws Exception {
        String s = "2024-7-4";
        System.out.println(DateUtils.parseDate(s, "yyyy-M-d"));
    }

    @Test
    public void test3() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2024-1-4 00:00:00");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    @Test
    public void test4() {
        String s = "[{'1':'1'}, {'2':'2'}]";
        JSONArray j = JSONArray.parseArray(s);
        System.out.println(j);
    }

    @Test
    public void test5() {
        String s = "Aug 14, 2024 1:44:22 PM";
    }
}
