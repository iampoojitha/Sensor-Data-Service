package com.project.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.project.receiver")
public class ReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiverApplication.class, args);
	}

}
