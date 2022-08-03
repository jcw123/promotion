package com.im.sky.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FastjsonTest {

    public HashMap<String,Object> fields = new HashMap<>();

    @Test
    public void testBug(){
        //序列化
        FastjsonTest test = new FastjsonTest();
        test.fields.put("child",test);
        System.out.println(JSONObject.toJSONString(test));
        String out = JSONObject.toJSONString(test,new SerializeConfig(true), SerializerFeature.WriteClassName);
        System.out.println(out);

        FastjsonTest o = JSONObject.parseObject(out,FastjsonTest.class, new ParserConfig(true)); //输出 {"@type":"com.alibaba.fastjson.Test","fields":{"@type":"java.util.HashMap","child":{"$ref":".."}}}
        System.out.println(o.fields.get("child"));
        Assert.assertTrue(o instanceof FastjsonTest);
        Assert.assertTrue(o.fields.get("child") instanceof FastjsonTest); //失败，实际是个hashmap,应是引用com.alibaba.fastjson.Test才对
    }

    @Test
    public void test2() {
        Map<String, Object> map = new HashMap<>();
        FastjsonTest test = new FastjsonTest();
        map.put("1", test);
        Map map2 = JSON.parseObject(JSON.toJSONString(map));
        System.out.println(map2.get("1").getClass().getTypeName());
    }

}
