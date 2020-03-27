package com.htp;

import com.htp.config.DatabaseConfig;
import com.htp.config.SecurityConfig;
import com.htp.config.SwaggerConfig;
import com.htp.config.TransactionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.htp"},
        exclude = {})
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
