package com.htp.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("datasource")
public class DatabaseConfig {
    private String driverName;
    private String url;
    private String login;
    private String password;
    private String initialSize;
    private String maxActive;

    @Bean(name = "dataSource")
    public BasicDataSource getDatasource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        dataSource.setInitialSize(Integer.valueOf(Objects.requireNonNull(initialSize)));
        dataSource.setMaxActive(Integer.valueOf(Objects.requireNonNull(maxActive)));
        return dataSource;
    }
}
