package com.htp.controller.converter;

import com.htp.controller.requests.OrderCreateRequest;
import com.htp.domain.DesignShirt;
import com.htp.domain.Order;
import com.htp.repository.DesignShirtRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public abstract class OrderRequestConverter<S, T> extends EntityConverter<S, T> {

	private DesignShirtRepository designShirtRepo;
	private PasswordEncoder encoder;

	protected Order convertToOrder(Order order, OrderCreateRequest request) {

		Set<DesignShirt> designShirts =
				designShirtRepo.findByIdInAndDeletedFalse(
						request.getDesignShirtIdSet().stream()
								.map(x -> Long.valueOf(x))
								.collect(Collectors.toList()));

		order.setCity(request.getCity());
		order.setStreet(request.getStreet());
		order.setZip(request.getZip());
		order.setCardNumber(encoder.encode(request.getCardNumber()));
		order.setCardExpiration(encoder.encode(request.getCardExpiration()));
		order.setCardCVV(encoder.encode(request.getCardCVV()));
		order.setPlacedAt(new Date());
		order.setDesignShirts(designShirts);

		return order;
	}

	@Autowired
	private void setShirtRepo(DesignShirtRepository designShirtRepo) {
		this.designShirtRepo = designShirtRepo;
	}

	@Autowired
	private void setPasswordEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
