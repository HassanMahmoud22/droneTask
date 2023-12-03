package com.example.dronetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestDroneTaskApplication {

	public static void main(String[] args) {
		SpringApplication.from(DroneTaskApplication::main).with(TestDroneTaskApplication.class).run(args);
	}

}
