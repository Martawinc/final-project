package com.htp.controller.converter;

import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class EntityConverter<S, R> implements Converter<S, R> {
  @PersistenceContext protected EntityManager entityManager;

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}
