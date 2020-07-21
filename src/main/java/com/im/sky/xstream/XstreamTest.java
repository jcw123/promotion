package com.im.sky.xstream;

import com.im.sky.xstream.domain.People;
import com.im.sky.xstream.domain.Son;
import com.thoughtworks.xstream.XStream;

/**
 * @author jiangchangwei
 * @date 2020-7-8 下午 5:10
 **/
public class XstreamTest {

    public static void main(String[] args) {
        XStream xStream = new XStream();
//        xStream.autodetectAnnotations(true);
        xStream.alias("hehe", People.class);
        xStream.aliasAttribute("kk","son");
        xStream.aliasAttribute("hehe","jj");
        People pe = new People();
        pe.setAge(32);
        pe.setName("孙行者");
        Son son = new Son();
        son.setAge(15);
        son.setName("小孙");
        pe.setSon(son);
        System.out.println(xStream.toXML(pe));
    }
}
