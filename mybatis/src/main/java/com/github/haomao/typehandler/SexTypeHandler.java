package com.github.haomao.typehandler;

import com.github.haomao.dto.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
//声明jdbcType为整形
@MappedJdbcTypes(JdbcType.INTEGER)
//声明javaType
@MappedTypes(SexEnum.class)
public class SexTypeHandler extends BaseTypeHandler<SexEnum>{

    //设置非空性别参数
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int idx, SexEnum sexEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(idx,sexEnum.getId());
    }

    //通过列名读取性别
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String column) throws SQLException {
        int sexId = resultSet.getInt(column);
        if (sexId != 1 && sexId != 2){
            return null;
        }
        return SexEnum.getEnumById(sexId);
    }

    //通过下标读取性别
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int idx) throws SQLException {
        int sexId = resultSet.getInt(idx);
        if (sexId != 1 && sexId != 2){
            return null;
        }
        return SexEnum.getEnumById(sexId);
    }

    //通过存储过程读取性别
    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int idx) throws SQLException {
        int sexId = callableStatement.getInt(idx);
        if (sexId != 1 && sexId != 2){
            return null;
        }
        return SexEnum.getEnumById(sexId);
    }
}
