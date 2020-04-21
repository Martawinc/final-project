package com.htp.controller.converter;

import com.htp.controller.requests.OrderUpdateRequest;
import com.htp.domain.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

import static java.util.Optional.ofNullable;

@Component
public class OrderFullUpdateRequestConverter
		extends OrderRequestConverter<OrderUpdateRequest, Order> {

	@Override
	public Order convert(OrderUpdateRequest request) {
		Order order =
				ofNullable(entityManager.find(Order.class, request.getId()))
						.orElseThrow(EntityNotFoundException::new);
		return convertToOrder(order, request);
	}
}
