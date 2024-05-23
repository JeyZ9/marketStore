package com.app.marketstore.service.impl;

import com.app.marketstore.model.Category;
import com.app.marketstore.repository.CategoryRepository;
import com.app.marketstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category readCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> readCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void updateCategory(Long categoryId, Category newCategory){
        Category category = categoryRepository.findById(categoryId).get();
        category.setCategoryName(newCategory.getCategoryName());
        categoryRepository.save(category);
    }
}
