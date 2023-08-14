package com.oranic.org;

import com.oranic.org.components.DefaultUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrganicApplication {

	@Autowired
	private DefaultUser defaultUser;
	public static void main(String[] args) {
		SpringApplication.run(OrganicApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

	}

}
