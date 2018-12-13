package com.github.haomao;

import com.github.haomao.jdbctemplate.dto.SexEnum;
import com.github.haomao.jdbctemplate.dto.User;
import com.github.haomao.jdbctemplate.JdbcTemplateUserService;
import com.github.haomao.jpa.repository.JpaUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private JdbcTemplateUserService userTemplate;
//	@Before
//	public void insertTest(){
//		User user = new User();
//		user.setUserName("ggq");
//		user.setSex(SexEnum.MALE);
//		user.setNote("测试");
//		int i = userTemplate.insertUser(user);
//		Assert.assertTrue(i == 1);
//	}

	@Test
//	@Transactional
	public void getUser1Test(){
		System.out.println("---------getUser----------");
		User user = userTemplate.getUser(1l);
		System.out.println(user.toString());
		System.out.println("---------getUser----------");

		System.out.println("---------getUser2----------");
		User user2 = userTemplate.getUser2(1l);
		System.out.println(user2.toString());
		System.out.println("---------getUser2----------");

		System.out.println("---------getUser3----------");
		User user3 = userTemplate.getUser3(1l);
		System.out.println(user3.toString());
		System.out.println("---------getUser3----------");

		System.out.println("---------findUsers----------");
		List<User> users = userTemplate.findUsers();
		System.out.println(users);
		System.out.println("---------findUsers----------");
	}

	@Test
	public void updateUserTest(){
		User user = new User();
		user.setId(1l);
		user.setSex(SexEnum.MALE);
		user.setNote("test");
		user.setUserName("ROOT");
		int i = userTemplate.updateUser(user);
		Assert.assertTrue(i ==1);
	}
	@Test
	public void deleteTest(){
		int i = userTemplate.deleteUser(1l);
		Assert.assertTrue(i == 1);
	}

	@Autowired
	private JpaUserRepository jpaUserRepository;

	@Test
	public void getFindUserByIdTest(){
		com.github.haomao.jpa.dto.User user = jpaUserRepository.findById(1l).get();
		System.out.println(user);
	}

	@Test
	public void findUsers(){
		List<com.github.haomao.jpa.dto.User> users = jpaUserRepository.findUsers("ggq", "test");
		System.out.println(users);
	}

	@Test
	public void findByUserNameLike(){
		List<com.github.haomao.jpa.dto.User> users = jpaUserRepository.findByUserNameLike("%ggq%");
		System.out.println(users);
	}

	@Test
	public void getUserByIdTest(){
		com.github.haomao.jpa.dto.User user = jpaUserRepository.getUserById(1l);
		System.out.println(user);
	}

	@Test
	public void findUserLikeTest(){
		List<com.github.haomao.jpa.dto.User> users = jpaUserRepository.findByUserNameLikeOrNoteLike("%ggq%", "%test%");
		System.out.println(users);
	}
}
