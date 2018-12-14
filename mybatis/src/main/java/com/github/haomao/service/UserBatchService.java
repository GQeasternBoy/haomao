package com.github.haomao.service;

import com.github.haomao.dto.User;

import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/13
 * @Description:
 */
public interface UserBatchService {

    int insertUsers(List<User> users);
}
