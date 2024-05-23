package com.app.marketstore.service.impl;

import com.app.marketstore.dto.CartDTO;
import com.app.marketstore.dto.CartItemDTO;
import com.app.marketstore.dto.CheckoutItemDTO;
import com.app.marketstore.exceptions.OrderNotFoundException;
import com.app.marketstore.model.Order;
import com.app.marketstore.model.OrderItem;
import com.app.marketstore.model.User;
import com.app.marketstore.repository.OrderItemRepository;
import com.app.marketstore.repository.OrderRepository;
import com.app.marketstore.service.CartService;
import com.app.marketstore.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(CartService cartService, OrderRepository orderRepository, OrderItemRepository orderItemRepository){
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDTO checkoutItemDTO) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("USD")
                .setUnitAmount( (long) checkoutItemDTO.getPrice() * 100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDTO.getProductName())
                                .build()
                )
                .build();
    }

    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDTO checkoutItemDTO) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData((createPriceData(checkoutItemDTO)))
                .setQuantity(Long.parseLong((String.valueOf(checkoutItemDTO.getQuantity()))))
                .build();
    }

    @Override
    public Session createSession(List<CheckoutItemDTO> checkoutItemDTOList) throws StripeException {
        String successURL = baseURL + "payment/success";

        String failedURL = baseURL + "payment/failed";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        for(CheckoutItemDTO checkoutItemDTO : checkoutItemDTOList){
            sessionItemsList.add(createSessionLineItem(checkoutItemDTO));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    @Override
    public void placeOrder(User user, String sessionId){
        CartDTO cartDTO = cartService.listCartItems(user);

        List<CartItemDTO> cartItemDTOList = cartDTO.getCartItemDTOS();

        Order newOrder = new Order();

        newOrder.setCreatedDate(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDTO.getTotalCost());
        orderRepository.save(newOrder);

        for(CartItemDTO cartItemDTO : cartItemDTOList){
            OrderItem orderItem = new OrderItem();

            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDTO.getProduct().getPrice());
            orderItem.setProduct(cartItemDTO.getProduct());
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setOrder(newOrder);

            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public List<Order> listOrder(User user){
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    @Override
    public Order getOrder(Long id, User user) throws OrderNotFoundException{
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order id is invalid");
        }

        Order order = optionalOrder.get();

        if(order.getUser() != user){
            throw new OrderNotFoundException("Order does not belong to this user");
        }

        return order;
    }




}
