package com.im.sky;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@RunWith(JUnit4.class)
public class JacksonTest {

    XmlMapper objectMapper;

    @Before
    public void before() {
         objectMapper = new XmlMapper();
    }


    @Test
    public void test() throws Exception{
        MyObject myObject = new MyObject();
        myObject.setName("1");
        String s = objectMapper.writeValueAsString(myObject);
        System.out.println(s);
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "test")
    public static class MyObject {

        @Setter
        @Getter
        @XmlElement(name = "name")
        private String name;
    }
}
