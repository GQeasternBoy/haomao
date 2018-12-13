package com.github.haomao.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
@Alias(value = "user")
@Data
public class User {

    private Long id;

    private String userName;

    //这里需要使用typeHandler进行转换
    private SexEnum sex;

    private String note;
}
