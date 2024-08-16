package com.im.sky;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-08-06 20:59
 **/
public class AviatorTest {

    @Test
    public void test2() throws Exception {
        Map<String, Object> env = new HashMap<>();
        AviatorEvaluator.addInstanceFunctions("m", M.class);
        env.put("m", new M());
        String t = (String)AviatorEvaluator.execute("m.test(m)", env);
        System.out.println(t);
    }

    public static class M {
        public String test() {
            return "tt";
        }
    }
}
