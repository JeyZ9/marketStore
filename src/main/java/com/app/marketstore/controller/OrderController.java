package com.app.marketstore.controller;

import com.app.marketstore.config.ApiResponse;
import com.app.marketstore.dto.CheckoutItemDTO;
import com.app.marketstore.dto.StripeResponse;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.OrderNotFoundException;
import com.app.marketstore.model.Order;
import com.app.marketstore.model.User;
import com.app.marketstore.service.AuthenticationService;
import com.app.marketstore.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "order", description = "The order controller")
public class OrderController {

    private final OrderService orderService;

    private final AuthenticationService authenticationService;

    @Autowired
    public OrderController(OrderService orderService, AuthenticationService authenticationService) {
        this.orderService = orderService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Create checkout session", description = "Create a new checkout session for payment")
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDTO> checkoutItemDTOList) throws StripeException{

        Session session = orderService.createSession(checkoutItemDTOList);

        StripeResponse stripeResponse = new StripeResponse(session.getId());

        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    @Operation(summary = "place order", description = "Add a new place order")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId) throws AuthenticationFailException{
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        orderService.placeOrder(user, sessionId);

        return new ResponseEntity<>(new ApiResponse(true, "Order placed successfully!"), HttpStatus.OK);
    }

    @Operation(summary = "Get All Order", description = "Get all order of a user from token")
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrder(@RequestParam("token") String token) throws AuthenticationFailException{
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        List<Order> orderList = orderService.listOrder(user);

        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Operation(summary = "Get the order by id", description = "Get the order by id from token")
    @GetMapping("/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable("orderId") Long id, @RequestParam("token") String token) throws AuthenticationFailException, OrderNotFoundException {

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        Order order = orderService.getOrder(id, user);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
