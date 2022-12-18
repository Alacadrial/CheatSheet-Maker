package com.spring.practice.cheatsheetmaker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@Getter
@Setter
@NoArgsConstructor
public class ApplicationConfig {
  @Value("${DATABASE_USERNAME}")
  private String mysqlUsername;
  @Value("${DATABASE_PASSWORD}")
  private String mysqlPassword;
  @Value("${MONGODB_CONNECTION_STRING}")
  private String mongoDbConnectionString;
  @Value("${JWT_SECRET}")
  private String jwtSecret;
}
