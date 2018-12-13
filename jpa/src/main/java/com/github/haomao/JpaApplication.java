package com.github.haomao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//定义JPA接口扫描包路径
@EnableJpaRepositories(basePackages = "com.github.haomao.jpa.repository")
//定义实体Bean扫描包路径
@EntityScan(basePackages = "com.github.haomao.jpa.dto")
public class JpaApplication{

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
