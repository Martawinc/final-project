package com.htp.repository;

import com.htp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository
    extends CrudRepository<Order, String>, JpaRepository<Order, String> {}
