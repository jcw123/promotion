package com.im.sky.protostuff.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Beijing {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Wall wall;

    @Setter
    @Getter
    private List<String> citys;


    @Getter
    @Setter
    private String[] rivers;
}
