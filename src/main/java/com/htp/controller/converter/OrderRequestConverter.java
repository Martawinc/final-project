package com.htp.controller.converter;

import com.htp.controller.requests.OrderCreateRequest;
import com.htp.domain.DesignShirt;
import com.htp.domain.Order;
import com.htp.repository.DesignShirtRepository;
import com.htp.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public abstract class OrderRequestConverter<S, T> extends EntityConverter<S, T> {

  private UserRepository userRepo;
  private DesignShirtRepository designShirtRepo;

  @Autowired
  private void setShirtRepo(UserRepository userRepo, DesignShirtRepository designShirtRepo) {
    this.userRepo = userRepo;
    this.designShirtRepo = designShirtRepo;
  }

  protected Order convertToOrder(Order order, OrderCreateRequest request) {

    Set<DesignShirt> designShirts =
        designShirtRepo.findByIdInAndDeletedFalse(
            request.getDesignShirtIdSet().stream()
                .map(x -> Long.valueOf(x))
                .collect(Collectors.toList()));

    order.setCity(request.getCity());
    order.setStreet(request.getStreet());
    order.setZip(request.getZip());
    order.setCardNumber(request.getCardNumber());
    order.setCardExpiration(request.getCardExpiration());
    order.setCardCVV(request.getCardCVV());
    order.setCardCVV(request.getCardCVV());
    order.setPlacedAt(new Date());
    order.setDesignShirts(designShirts);

    return order;
  }
}
