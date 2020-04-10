package com.htp.controller.converter;

import com.htp.controller.requests.OrderCreateRequest;
import com.htp.domain.Order;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class OrderCreateRequestConverter extends OrderRequestConverter<OrderCreateRequest, Order> {

  @Override
  public Order convert(OrderCreateRequest request) {
    Order order = new Order();
    return convertToOrder(order, request);
  }
}
