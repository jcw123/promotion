package com.im.sky.netty.example.demo1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 3:33
 * 人的七大情绪的枚举
 **/
public enum EmotionEnums {

    HAPPY(1, "喜"),
    ANGRY(2, "怒"),
    WORRIED(3, "忧"),
    THOUGHTFUL(4, "思"),
    SAD(5, "悲"),
    FEARFUL(6, "恐"),
    FRIGHTENED(7, "惊");

    private EmotionEnums(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    private static final Map<String, EmotionEnums> map = new HashMap<>();

    static {
        for(EmotionEnums e : EmotionEnums.values()) {
            map.put(e.type + "_" + e.msg, e);
        }
    }

    private int type;

    private String msg;

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public static EmotionEnums get(int type, String msg) {
        return map.get(type + "_" + msg);
    }
}
