package com.github.haomao.jdbctemplate.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:ggq
 * @Date:2018/12/10
 * @Description:
 */
public enum SexEnum {

    MALE(1,"男"),
    FEMALE(2,"女");

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    SexEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SexEnum getEnumById(int id){
        for (SexEnum sex : SexEnum.values()){
            if (sex.getId() == id){
                return sex;
            }
        }
        return null;
    }
}
