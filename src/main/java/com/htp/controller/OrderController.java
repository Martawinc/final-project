package com.htp.controller;

import com.htp.controller.requests.OrderCreateRequest;
import com.htp.domain.DesignShirt;
import com.htp.domain.Order;
import com.htp.repository.DesignShirtRepository;
import com.htp.repository.OrderRepository;
import com.htp.repository.UserRepository;
import com.htp.security.Headers;
import com.htp.security.PrincipalUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
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
  private final ConversionService conversionService;

  @PostMapping("/create")
  @Transactional
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true)
  })
  public ResponseEntity<Order> createOrder(
      @RequestBody @Valid OrderCreateRequest request, @ApiIgnore Principal principal) {
    Order convertedOrder = conversionService.convert(request, Order.class);

    convertedOrder.setUser(userRepo.findById(PrincipalUtil.getPrincipalId(principal)).get());
    return new ResponseEntity<>(orderRepo.saveAndFlush(convertedOrder), HttpStatus.CREATED);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Order> test() {
    Order order = new Order();
    order.setCity("Minsk");
    order.setStreet("Lenina");
    order.setZip("200200");
    order.setCardNumber("9876678900007654");
    order.setCardExpiration("12/22");
    order.setCardCVV("123");
    order.setPlacedAt(new Date());
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
