package com.app.marketstore.service;

import com.app.marketstore.dto.ProductDTO;
import com.app.marketstore.exceptions.ProductNotExistException;
import com.app.marketstore.model.Category;
import com.app.marketstore.model.Product;

import java.util.List;

public interface ProductService {

    void addProduct(ProductDTO productDTO, Category category);

    List<ProductDTO> listProducts();

    void updateProduct(Long productId, ProductDTO productDTO, Category category);

//    เมดทอดค้นหาสินค้าถ้าไม่มีสินค้าที่ตรงกับ id, throws จะทำหน้าที่ดึงข้อผิดพลาดจากคลาส ProductNotExistsException มาแสดง
    Product getProductById(Long productId) throws ProductNotExistException;
}
