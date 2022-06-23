package com.mark.waif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaifApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(WaifApplication.class, args);
	}
}
