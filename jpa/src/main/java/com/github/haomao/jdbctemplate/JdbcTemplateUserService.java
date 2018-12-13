package com.github.haomao.jdbctemplate;

import com.github.haomao.jdbctemplate.dto.User;

import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/10
 * @Description:
 */
public interface JdbcTemplateUserService {

    User getUser(Long id);

    User getUser2(Long id);

    User getUser3(Long id);

    List<User> findUsers();

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(Long id);
}
