package com.spring.practice.cheatsheetmaker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.practice.cheatsheetmaker.filter.JwtFilter;
import com.spring.practice.cheatsheetmaker.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtFilter jwtRequestFilter;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  // For calls to /api endpoint this filterChain is used for authorization Authorization is done with jwt.
  @Bean
  @Order(1)
  public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
    return http
        .securityMatcher("/api/**")
        .userDetailsService(userDetailsService)
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/api/auth").permitAll();
          auth.requestMatchers("/api/public/**").permitAll();
          auth.requestMatchers("/api/**").authenticated();
          auth.anyRequest().denyAll();
        })
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  // For calls that are not directed to /api endpoint, below filterChain is used. Authorization is done with login, sessionid.
  @Bean
  @Order(2)
  public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
    return http
        .userDetailsService(userDetailsService)
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/admin/**").hasRole("ROLE_ADMIN");
          auth.requestMatchers("/user/**").hasAnyRole("ROLE_USER", "ROLE_ADMIN");
          auth.requestMatchers("/logout").authenticated();
          auth.requestMatchers("/login", "/register").anonymous();
          auth.requestMatchers("/", "/public/**", "/test/**").permitAll();
          auth.anyRequest().authenticated();
        })
        .formLogin()
        .and()
        .logout().permitAll()
        .logoutSuccessUrl("/")
        .and()
        .build();
  }
}
