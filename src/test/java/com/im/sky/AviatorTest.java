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

    @Test
    public void test3() {
        Map<String, Object> env = new HashMap<>();
        env.put("test", null);
        String expr = "test2 == 3";
        boolean res = (boolean)AviatorEvaluator.execute(expr, env);
        System.out.println(res);
    }

    public static class M {
        public String test() {
            return "tt";
        }
    }
}
