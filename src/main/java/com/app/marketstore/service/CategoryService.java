package com.app.marketstore.service;

import com.app.marketstore.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
//    ดึงหมวดหมู่ตามชื่อ
    Category readCategoryByName(String categoryName);
//    สร้างหมวดหมู่ใหม่
    void createCategory(Category category);
//    ดึงรายการของหมวดหมู่ทั้งหมด
    List<Category> listCategory();
//    ดึงหมวดหมู่ตาม ID โดยคืนค่าเป็น Optional<Category>
    Optional<Category> readCategoryById(Long categoryId);
//    อัพเดตข้อมูลหมวดหมู่ตาม ID
    void updateCategory(Long categoryId, Category newCategory);
}
