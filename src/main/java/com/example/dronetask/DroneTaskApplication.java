package com.example.dronetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.dronetask"})
@EntityScan("com.example.dronetask")
@EnableJpaRepositories("com.example.dronetask")
public class DroneTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneTaskApplication.class, args);
	}

}
