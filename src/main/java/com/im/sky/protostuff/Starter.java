package com.im.sky.protostuff;

import com.im.sky.protostuff.model.Beijing;
import com.im.sky.protostuff.model.Wall;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Starter {

    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE * 2);

    public static void main(String[] args) throws Exception {
        Schema<Beijing> schema = RuntimeSchema.getSchema(Beijing.class);
        Beijing beijing = new Beijing();
        beijing.setName("北京");
        Wall wall = new Wall();
        wall.setName("城墙");
        beijing.setWall(wall);
        List<String> citys = new ArrayList<>();
        citys.add("1");
        citys.add("2");
        beijing.setCitys(citys);
        String[] rivers = new String[2];
        rivers[0] = "river1";
        rivers[1] = "river2";
        beijing.setRivers(rivers);
        byte[] bytes = ProtostuffIOUtil.toByteArray(beijing, schema, BUFFER);
        Beijing beijing2 = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, beijing2, schema);
        System.out.println(beijing2.getCitys().toString());
        System.out.println(Arrays.toString(beijing2.getRivers()));
    }

    private void serialize() {

    }

    private void deserialize() {

    }
}
