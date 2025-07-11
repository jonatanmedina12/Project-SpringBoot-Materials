package com.management.materials;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EnableScheduling
public class MaterialsApplication {
	private static final Logger logger = LoggerFactory.getLogger(MaterialsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MaterialsApplication.class, args);

	}


}
