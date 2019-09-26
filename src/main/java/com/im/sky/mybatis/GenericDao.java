package com.im.sky.mybatis;

import java.util.List;
import java.util.Map;

/**
 * @Author: jiangcw
 * @Date: 2019-9-16 下午 9:04
 * @Version 1.0
 */
public interface GenericDao {

    Object findAnyOne();

    List<Object> findObjectList(int size);

    boolean insert(Map<String, Object> map);

    boolean update(Map<String, Object> map);

    boolean delete(Map<String, Object> map);
}
