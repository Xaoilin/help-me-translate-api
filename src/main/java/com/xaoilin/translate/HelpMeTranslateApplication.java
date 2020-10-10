package com.xaoilin.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HelpMeTranslateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpMeTranslateApplication.class, args);
	}

}
