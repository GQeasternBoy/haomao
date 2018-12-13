package com.github.haomao.jpa.converter;

import com.github.haomao.jdbctemplate.dto.SexEnum;

import javax.persistence.AttributeConverter;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description: 性别转换器
 */
public class SexConverter implements AttributeConverter<SexEnum,Integer> {

    //将枚举转换为数据库序列
    @Override
    public Integer convertToDatabaseColumn(SexEnum sexEnum) {
        return sexEnum.getId();
    }

    //将数据库列转换为枚举
    @Override
    public SexEnum convertToEntityAttribute(Integer id) {
        return SexEnum.getEnumById(id);
    }
}
