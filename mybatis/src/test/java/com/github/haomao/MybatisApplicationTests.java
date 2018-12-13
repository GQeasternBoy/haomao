package com.github.haomao;

import com.github.haomao.dao.UserDao;
import com.github.haomao.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private UserDao userDao;

	@Test
	public void getUserTest(){
		User user = userDao.getUser(1l);
		System.out.println(user);
	}
}
