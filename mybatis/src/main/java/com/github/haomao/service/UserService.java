package com.github.haomao.service;

import com.github.haomao.dao.UserDao;
import com.github.haomao.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:ggq
 * @Date:2018/12/12
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void getUser(){
        User user = userDao.getUser(1l);
        System.out.println(user);
    }

    public int saveUser(User user){
        int update = userDao.insertUser(user);
        return update;
    }
}
