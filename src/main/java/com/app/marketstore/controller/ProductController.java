package com.app.marketstore.controller;

import com.app.marketstore.config.ApiResponse;
import com.app.marketstore.dto.ProductDTO;
import com.app.marketstore.model.Category;
import com.app.marketstore.service.CategoryService;
import com.app.marketstore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO productDTO){
        Optional<Category> optionalCategory = categoryService.readCategoryById(productDTO.getCategoryId());
        if(!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "Invalid category"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDTO, category);
        return new ResponseEntity<>(new ApiResponse(true, "Product added successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> productDTOS = productService.listProducts();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO){
        Optional<Category> optionalCategory = categoryService.readCategoryById(productDTO.getCategoryId());

        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "Invalid category"), HttpStatus.CONFLICT);
        }

        Category category = optionalCategory.get();
        productService.updateProduct(productId, productDTO, category);
        return new ResponseEntity<>(new ApiResponse(true, "product updated successfully!"), HttpStatus.OK);
    }
}
