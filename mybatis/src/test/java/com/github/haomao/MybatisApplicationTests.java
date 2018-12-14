package com.github.haomao;

import com.github.haomao.dao.UserDao;
import com.github.haomao.dto.SexEnum;
import com.github.haomao.dto.User;
import com.github.haomao.service.UserBatchService;
import com.github.haomao.service.impl.UserBatchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private UserBatchService userBatchService;

	@Test
	public void batchInsert(){
		User oneUser = new User();
		oneUser.setUserName("mayun");
		oneUser.setSex(SexEnum.MALE);
		oneUser.setNote("alibaba");

		User twoUser = new User();
		twoUser.setUserName("mahuateng");
		twoUser.setSex(SexEnum.MALE);
		twoUser.setNote("tengxun");

		List<User> users = new ArrayList<>();
		users.add(oneUser);
		users.add(twoUser);

		System.out.println("------------batchInsert---------");
		int update = userBatchService.insertUsers(users);
		System.out.println(users);
	}
}
