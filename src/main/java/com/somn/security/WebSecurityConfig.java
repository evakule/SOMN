package com.somn.security;

import com.somn.service.CustomerEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  private static final String ADMIN_ENDPOINT = "/api/v1/customers/**";
  private static final String ACCOUNTANT_AND_CUSTOMER_ENDPOINT = "/api/v1/accounts/**";
  
  @Autowired
  private CustomerEntityService customerEntityService;
  
  @Autowired
  private AuthenticationEntryPoint authEntryPoint;
  
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
        .antMatchers(ACCOUNTANT_AND_CUSTOMER_ENDPOINT)
        .hasAnyRole("ACCOUNTANT", "CUSTOMER")
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authEntryPoint);
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customerEntityService);
  }
}
