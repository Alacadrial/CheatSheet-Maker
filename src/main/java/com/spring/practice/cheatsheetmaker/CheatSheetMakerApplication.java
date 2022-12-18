package com.spring.practice.cheatsheetmaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.spring.practice.cheatsheetmaker.config.ApplicationConfig;
import com.spring.practice.cheatsheetmaker.repository.AuthorityRepository;
import com.spring.practice.cheatsheetmaker.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { UserRepository.class, AuthorityRepository.class})
public class CheatSheetMakerApplication {

	@Autowired
	private ApplicationConfig applicationConfig;

	@PostConstruct
	public void showApplicationConfig() {
		log.info("<-- Application Config -->");
		log.info(applicationConfig.getMysqlUsername());
		//log.info(appConfig.getMysql_password());
		//log.info(applicationConfig.getMongoDbConnectionString());
	}

	public static void main(String[] args) {
		SpringApplication.run(CheatSheetMakerApplication.class, args);
	}

}
