package com.htp;

import com.htp.config.DatabaseConfig;
import com.htp.config.SecurityConfig;
import com.htp.config.SwaggerConfig;
import com.htp.config.TransactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.htp"},
        exclude = {JacksonAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class})
@Import({
        DatabaseConfig.class,
        TransactionConfig.class,
        SecurityConfig.class,
        SwaggerConfig.class
})
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
