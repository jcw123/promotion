package com.im.sky.javacore.classloader.t1;

import com.im.sky.javacore.classloader.LinkageTest;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-03 21:34
 **/
public class PeopleHolder {

    Man people = new Man();

    public PeopleHolder() {
        System.out.println("PeopleHolder class:" + this.getClass().getClassLoader());
        ManHolder manHolder = new ManHolder();
        System.out.println("ManHolder class:" + manHolder.getClass().getClassLoader());
        System.out.println("manHolder.man:" + manHolder.man.getClass().getClassLoader());
        this.people = manHolder.man;
        System.out.println("this.people:" + this.people.getClass().getClassLoader());
    }
}
