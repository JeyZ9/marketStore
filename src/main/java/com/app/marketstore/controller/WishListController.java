package com.app.marketstore.controller;

import com.app.marketstore.config.ApiResponse;
import com.app.marketstore.dto.ProductDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.model.Product;
import com.app.marketstore.model.User;
import com.app.marketstore.model.WishList;
import com.app.marketstore.repository.ProductRepository;
import com.app.marketstore.service.AuthenticationService;
import com.app.marketstore.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
@Tag(name = "wishlist", description = "The wish list controller")
public class WishListController {

    private final WishListService wishListService;
    private final AuthenticationService authenticationService;
    private final ProductRepository productRepository;

    @Autowired
    public WishListController(WishListService wishListService, AuthenticationService authenticationService, ProductRepository productRepository) {
        this.wishListService = wishListService;
        this.authenticationService = authenticationService;
        this.productRepository = productRepository;
    }

    @Operation(summary = "Add a wish list", description = "Add a product to the wish list")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody ProductDTO productDTO, @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        Product product = productRepository.getById(productDTO.getId());

        WishList wishLIst = new WishList(user, product);

        wishListService.createWishList(wishLIst);

        return new ResponseEntity<>(new ApiResponse(true, "Added to wish list"), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a wish list", description = "Get a product in wish list by token")
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDTO>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException{
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        List<WishList> wishLists = wishListService.readWishList(user);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (WishList wishList : wishLists){
            productDTOS.add(new ProductDTO(wishList.getProduct()));
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
}
