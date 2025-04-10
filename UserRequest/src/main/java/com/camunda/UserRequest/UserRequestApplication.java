package com.camunda.UserRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.spring.client.annotation.Deployment;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@Deployment(resources = {"classpath:/bpmn/*.bpmn", "classpath:/dmn/*.dmn"})
public class UserRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRequestApplication.class, args);
	}

}
