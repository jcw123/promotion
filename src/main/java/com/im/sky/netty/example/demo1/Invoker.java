package com.im.sky.netty.example.demo1;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 4:55
 **/
public class Invoker {

    public static String invoke(EmotionEnums emotionEnums) {
        if(emotionEnums == EmotionEnums.HAPPY) {
            return "人生苦短，请快乐的生活吧，没有什么坎是过不去的!";
        } else if (emotionEnums == EmotionEnums.ANGRY) {
            return "不要生气，会长皱纹的";
        } else if(emotionEnums == EmotionEnums.FEARFUL) {
            return "请克服内心的恐惧，无惧未来";
        } else if(emotionEnums == EmotionEnums.FRIGHTENED) {
            return "对不起呀，吓到你了";
        } else if(emotionEnums == EmotionEnums.SAD) {
            return "男儿有泪不轻弹，只是未到伤心处";
        } else if(emotionEnums == EmotionEnums.THOUGHTFUL) {
            return "多思考，才能更快的成长";
        } else if(emotionEnums == EmotionEnums.WORRIED) {
            return "没什么好担心的，船到桥头自然直";
        }
        return "";
    }
}
