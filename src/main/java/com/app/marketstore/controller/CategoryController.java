package com.app.marketstore.controller;

import com.app.marketstore.config.ApiResponse;
import com.app.marketstore.model.Category;
import com.app.marketstore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        if (Objects.nonNull(service.readCategoryByName(category.getCategoryName()))){
            return new ResponseEntity<>(new ApiResponse(false, "category already exists!"), HttpStatus.CONFLICT);
        }

        service.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "created the category successfully!"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categoryList = service.listCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PutMapping("update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category newCategory){
        if (Objects.nonNull(service.readCategoryById(categoryId))){
            service.updateCategory(categoryId, newCategory);
            return new ResponseEntity<>(new ApiResponse(true, "category updated successfully!"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(false, "category does not exist!"), HttpStatus.NOT_FOUND);
    }
}
