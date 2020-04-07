package com.htp.controller;

import com.htp.domain.DesignShirt;
import com.htp.domain.Order;
import com.htp.repository.DesignShirtRepository;
import com.htp.repository.OrderRepository;
import com.htp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/test")
@RequiredArgsConstructor
public class OrderController {

  private final OrderRepository orderRepo;
  private final UserRepository userRepo;
  private final DesignShirtRepository designShirtRepo;

  @Transactional
  @PostMapping
  public ResponseEntity<Order> test() {
    Order order = new Order();
    order.setCity("Minsk");
    order.setStreet("Lenina");
    order.setZip("200200");
    order.setCardNumber("9876678900007654");
    order.setCardExpiration("12/22");
    order.setCardCVV("12/22");
    order.setPlaced_at(new Date());
    order.setUser(userRepo.findById(9L).get());
    Set<DesignShirt> designShirts = new HashSet<>();
    DesignShirt shirt = designShirtRepo.findById(2L).get();
    designShirts.add(shirt);
    shirt = designShirtRepo.findById(3L).get();
    designShirts.add(shirt);
    order.setDesignShirts(designShirts);

    return new ResponseEntity<>(orderRepo.saveAndFlush(order), HttpStatus.OK);
  }
}
