package com.htp.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class TransactionConfig {

  private final BasicDataSource dataSource;

  @Bean("transactionManager")
  public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
