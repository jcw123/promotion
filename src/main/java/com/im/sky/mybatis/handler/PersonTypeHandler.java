package com.im.sky.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.im.sky.mybatis.domain.Person;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jiangchangwei
 * @date 2020-11-4 下午 2:46
 *
 * 自定义类型转换器，实现jdbc数据类型和java数据类型的转换
 **/
@MappedJdbcTypes(JdbcType.VARCHAR)
public class PersonTypeHandler extends BaseTypeHandler<Person> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Person parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public Person getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String data = rs.getString(columnName);
        return JSON.parseObject(data, Person.class);
    }

    @Override
    public Person getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String data = rs.getString(columnIndex);
        return JSON.parseObject(data, Person.class);
    }

    @Override
    public Person getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String data = cs.getString(columnIndex);
        return JSON.parseObject(data, Person.class);
    }
}
