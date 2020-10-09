package com.im.sky.mysql.mvcc;

import lombok.Data;

/**
 * @author jiangchangwei
 * @date 2020-9-16 下午 8:20
 **/
@Data
public class Row implements Cloneable {

    public Row(long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Row() {

    }

    private long id;

    private int age;

    private String name;

    private long trx_id;

    private Row history;

    private boolean delete;

    /**
     * 只进行基本元素的克隆
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Row clone() throws CloneNotSupportedException {
        Row row = new Row();
        row.setId(this.id);
        row.setAge(this.age);
        row.setName(this.name);
        return row;
    }
}
