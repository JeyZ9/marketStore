package com.app.marketstore.controller;

import com.app.marketstore.config.ApiResponse;
import com.app.marketstore.dto.AddToCartDTO;
import com.app.marketstore.dto.CartDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.CartItemNotExistException;
import com.app.marketstore.exceptions.ProductNotExistException;
import com.app.marketstore.model.Product;
import com.app.marketstore.model.User;
import com.app.marketstore.service.AuthenticationService;
import com.app.marketstore.service.CartService;
import com.app.marketstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "cart", description = "The cart controller")
public class CartController {

    private final CartService cartService;

    private final AuthenticationService authenticationService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, AuthenticationService authenticationService, ProductService productService){
        this.cartService = cartService;
        this.authenticationService = authenticationService;
        this.productService = productService;
    }

    @Operation(summary = "Add product to cart", description = "add product to")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDTO addToCartDTO, @RequestParam("token") String token) throws ProductNotExistException, AuthenticationFailException{
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        Product product = productService.getProductById(addToCartDTO.getProductId());

        cartService.addToCart(addToCartDTO, product, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart successfully!"), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all product", description = "Get all products from cart")
    @GetMapping("/")
    public ResponseEntity<CartDTO> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException{
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        CartDTO cartDTO = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete cart item", description = "Delete all products from cart ID")
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long cartItemId, @RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException{

        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(cartItemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item removed successfully"), HttpStatus.OK);
    }
}
