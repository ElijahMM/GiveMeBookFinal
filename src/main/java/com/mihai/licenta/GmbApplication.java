package com.mihai.licenta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@SpringBootApplication
public class GmbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmbApplication.class, args);
	}

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}
}
