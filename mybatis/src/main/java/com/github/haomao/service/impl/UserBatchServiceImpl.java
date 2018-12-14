package com.github.haomao.service.impl;

import com.github.haomao.dto.User;
import com.github.haomao.service.UserBatchService;
import com.github.haomao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author:ggq
 * @Date:2018/12/13
 * @Description:
 */
@Service
public class UserBatchServiceImpl implements UserBatchService{

    @Autowired
    private UserService userService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int insertUsers(List<User> users) {
        int count = 0;
        for (User user : users){
            count += userService.saveUser(user);
        }
        return count;
    }
}
