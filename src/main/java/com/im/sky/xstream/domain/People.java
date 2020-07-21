package com.im.sky.xstream.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author jiangchangwei
 * @date 2020-7-8 下午 5:17
 **/
@XStreamAlias("People")
public class People {

    private String name;

    private int age;

    private Son son;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Son getSon() {
        return son;
    }

    public void setSon(Son son) {
        this.son = son;
    }
}
