package com.htp.config;

import com.htp.security.AuthenticationTokenFilter;
import com.htp.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final TokenUtils tokenUtils;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui/**",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**")
        .permitAll()
        .antMatchers("/registration/**").permitAll()
        .antMatchers("/login/**").permitAll()
        .antMatchers("/admin").hasRole("ADMIN")
        .antMatchers("/**").hasAnyRole("USER", "ADMIN");

    http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        return new AuthenticationTokenFilter(
                tokenUtils, userDetailsService, authenticationManagerBean());
    }
}
