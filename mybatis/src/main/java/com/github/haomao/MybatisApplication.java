package com.github.haomao;

import com.github.haomao.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.haomao.dao")
public class MybatisApplication{

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

}
