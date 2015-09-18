package com.feisystems.polrep.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.feisystems.polrep.domain" })
@EnableJpaRepositories("com.feisystems.polrep.infrastructure")
@ComponentScan(basePackages = { "com.feisystems.polrep.config",
		"com.feisystems.polrep.service", "com.feisystems.polrep.web" })
public class PolRepApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolRepApplication.class, args);
	}
}
