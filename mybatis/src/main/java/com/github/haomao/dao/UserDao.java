package com.github.haomao.dao;

import com.github.haomao.dto.User;
import org.springframework.stereotype.Repository;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
@Repository
public interface UserDao {

    User getUser(Long id);

    int insertUser(User user);
}
