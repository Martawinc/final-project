package com.htp.repository;

import com.htp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCityIgnoreCase (String city);
}
