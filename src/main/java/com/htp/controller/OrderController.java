package com.htp.controller;

import com.htp.controller.requests.OrderCreateRequest;
import com.htp.controller.requests.OrderUpdateRequest;
import com.htp.domain.Order;
import com.htp.repository.OrderRepository;
import com.htp.repository.UserRepository;
import com.htp.security.Headers;
import com.htp.security.PrincipalUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

  private final OrderRepository orderRepo;
  private final ConversionService conversionService;
  private final UserRepository userRepo;

  @ApiOperation(value = "Place an order")
  @ApiResponses({
    @ApiResponse(code = 201, message = "New order is created"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header" )
  })
  @Transactional(rollbackOn = Exception.class)
  @PostMapping("/order")
  public ResponseEntity<Order> createOrder(
      @RequestBody @Valid OrderCreateRequest request, @ApiIgnore Principal principal) {
    Order convertedOrder = conversionService.convert(request, Order.class);

    convertedOrder.setUser(userRepo.findById(PrincipalUtil.getPrincipalId(principal)).get());
    return new ResponseEntity<>(orderRepo.saveAndFlush(convertedOrder), HttpStatus.CREATED);
  }
  
  @ApiOperation(value = "Update selected order")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Selected order is updated"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header" )
  })
  @Transactional(rollbackOn = Exception.class)
  @PutMapping("/order")
  public ResponseEntity<Order> updateOrder(@RequestBody @Valid OrderUpdateRequest request) {
    Order convertedOrder = conversionService.convert(request, Order.class);
    return new ResponseEntity<>(orderRepo.saveAndFlush(convertedOrder), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Filter by city order delivered", notes = "find operation ignore case")
  @ApiResponses({
    @ApiResponse(code = 200, message = "Successful getting orders by selected city"),
    @ApiResponse(code = 204, message = "Order with selected city not found"),
    @ApiResponse(code = 500, message = "Server error, something wrong")
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = Headers.AUTH_TOKEN, value = "token", required = true, dataType = "string", paramType = "header" )
  })
  @GetMapping("/admin/order/city")
  public ResponseEntity<List<Order>> blankShirtBySize(
      @ApiParam(value = "City filter for orders") @RequestParam(name = "city") String city) {
    List<Order> orders = orderRepo.findByCityIgnoreCase(city);
    if (!orders.isEmpty()) {
      return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
  }
}
