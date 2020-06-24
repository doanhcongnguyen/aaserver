package com.dasan.aaserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@Slf4j
public class AaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AaServerApplication.class, args);
		log.info("Server start at localhost:9989");
	}

}
