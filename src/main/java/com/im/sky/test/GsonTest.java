package com.im.sky.test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-08-22 17:16
 **/
public class GsonTest {

    private static final Gson GSON = new Gson();

    public static void main(String[] args) {
        People people = new People();
        people.setAge(10);
        people.setName("jcw");
        System.out.println(GSON.toJson(people));
        String s = "{\"name\":\"jcw\",\"age\":10}";
        people = GSON.fromJson(s, People.class);
        System.out.println(people.name);
    }

    @Data
    private static class People {

        @SerializedName(value = "NAME2", alternate = {"NAME1"})
        private String name;

        private int age;
    }
}
