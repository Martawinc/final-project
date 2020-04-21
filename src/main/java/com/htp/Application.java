package com.htp;

import com.htp.config.AmazonS3Config;
import com.htp.config.DatabaseConfig;
import com.htp.config.JwtTokenConfig;
import com.htp.config.SecurityConfig;
import com.htp.config.SwaggerConfig;
import com.htp.config.TransactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication(
		scanBasePackages = {"com.htp"},
		exclude = {JacksonAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Import({
		AmazonS3Config.class,
		DatabaseConfig.class,
		TransactionConfig.class,
		SecurityConfig.class,
		JwtTokenConfig.class,
		SwaggerConfig.class
})
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
}
