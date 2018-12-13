package com.github.haomao.jdbctemplate.dto;

import lombok.Data;

/**
 * @Author:ggq
 * @Date:2018/12/10
 * @Description:
 */
@Data
public class User {

    private Long id;
    private String userName;
    private SexEnum sex;
    private String note;
}
