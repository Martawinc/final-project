package com.htp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder encoder() {
    return new StandardPasswordEncoder("66rt7o");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().permitAll();

    //    http
    //        .sessionManagement()
    //        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    //      .and()
    //        .authorizeRequests()
    //          .antMatchers("/registration")
    //            .permitAll()
    //          .antMatchers("/*")
    //            .permitAll()
    //     .hasAuthority("ROLE_USER")
    //     .and()
    //        .formLogin()
    //        .loginPage("/login")
    //     .and()
    //        .logout()
    //    ;
  }
}
