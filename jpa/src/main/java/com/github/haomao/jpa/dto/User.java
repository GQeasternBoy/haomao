package com.github.haomao.jpa.dto;

import com.github.haomao.jdbctemplate.dto.SexEnum;
import com.github.haomao.jpa.converter.SexConverter;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
//标明是一个实体类
@Entity(name = "user")
//定义映射的表
@Table(name = "t_user")
@Data
public class User {

    @Id
    //主键策略，递增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    private String userName;

    @Convert(converter = SexConverter.class)
    private SexEnum sex;

    @Column(name="note")
    private String note;
}
