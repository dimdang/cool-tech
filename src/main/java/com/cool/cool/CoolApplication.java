package com.cool.cool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cool.cool")
public class CoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoolApplication.class, args);
	}
}
