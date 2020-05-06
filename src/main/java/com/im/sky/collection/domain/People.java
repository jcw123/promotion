package com.im.sky.collection.domain;

/**
 * @author jiangchangwei
 * @date 2020-3-25 下午 4:08
 **/
public class People implements Comparable<People> {

    private int id;

    private int age;

    private String name;

    public People(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(People o) {
        if(o == null) {
            return -1;
        }
        if(this.id - o.getId() != 0) {
            return this.id - o.getId();
        }
        if(this.age - o.getAge() != 0) {
            return this.age - o.getAge();
        }
        if(this.name == null) {
            return -1;
        }
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof People)) {
            return false;
        }
        if(this.id != ((People) obj).getId()) {
            return false;
        }
        if(this.age != ((People) obj).getAge()) {
            return false;
        }
        return true;
    }
}
