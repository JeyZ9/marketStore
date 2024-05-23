package com.app.marketstore.service.impl;

import com.app.marketstore.dto.AddToCartDTO;
import com.app.marketstore.dto.CartDTO;
import com.app.marketstore.dto.CartItemDTO;
import com.app.marketstore.exceptions.CartItemNotExistException;
import com.app.marketstore.model.Cart;
import com.app.marketstore.model.Product;
import com.app.marketstore.model.User;
import com.app.marketstore.repository.CartRepository;
import com.app.marketstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository repository;

    @Autowired
    public CartServiceImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addToCart(AddToCartDTO addToCartDTO, Product product, User user){
        Cart cart = new Cart(addToCartDTO.getQuantity(), user, product);

        repository.save(cart);
    }

    @Override
    public CartDTO listCartItems(User user){
        List<Cart> cartList = repository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDTO> cartItemDTOS = new ArrayList<>();

        for(Cart cart : cartList){
            CartItemDTO cartItemDTO = new CartItemDTO(cart);

            cartItemDTOS.add(cartItemDTO);
        }

        double totalCost = 0;

        for(CartItemDTO cartItemDTO : cartItemDTOS){
            totalCost += cartItemDTO.getProduct().getPrice() * cartItemDTO.getQuantity();
        }

        return new CartDTO(cartItemDTOS, totalCost);
    }

    @Override
    public void deleteCartItem(Long cartItemId, User user) throws CartItemNotExistException {
        Optional<Cart> optionalCart = repository.findById(cartItemId);

        if(optionalCart.isEmpty()){
            throw new CartItemNotExistException("Cart item id now valid");
        }

        Cart cart = optionalCart.get();

        if(cart.getUser() != user){
            throw new CartItemNotExistException("Cart item does not belong to user");
        }

        repository.deleteById(cartItemId);
    }
}
