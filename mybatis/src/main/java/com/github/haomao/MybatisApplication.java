package com.github.haomao;

import com.github.haomao.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan(basePackages = "com.github.haomao.dao")
public class MybatisApplication{

	//注入事务管理器，由spring boot自动生成
	@Autowired
	PlatformTransactionManager transactionManager;

	@PostConstruct
	public void viewTransactionManager(){
		System.out.println("PlatformTransactionManager----->"+transactionManager.getClass().getName());
	}

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

}
