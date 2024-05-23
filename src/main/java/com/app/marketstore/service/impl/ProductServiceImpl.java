package com.app.marketstore.service.impl;

import com.app.marketstore.dto.ProductDTO;
import com.app.marketstore.exceptions.ProductNotExistException;
import com.app.marketstore.model.Category;
import com.app.marketstore.model.Product;
import com.app.marketstore.repository.ProductRepository;
import com.app.marketstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static Product getProductFromDTO(ProductDTO productDTO, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setMainImageUrl(productDTO.getMainImageUrl());
        product.setAdditionalImages(productDTO.getAdditionalImages());
        return product;
    }

    @Override
    public void addProduct(ProductDTO productDTO, Category category) {
        Product product = getProductFromDTO(productDTO, category);
        productRepository.save(product);
    }

//    เมธอด listProducts แปลงข้อมูลสินค้าจากเอนทิตี Product ไปยัง ProductDTO ก่อนส่งต่อไปยังผู้ใช้งาน โดยกระบวนการนี้ช่วยปกป้องข้อมูลภายในและทำให้การส่งข้อมูลมีประสิทธิภาพมากขึ้น รวมถึงช่วยแยกความรับผิดชอบระหว่างชั้นต่าง ๆ ในแอปพลิเคชัน.

    @Override
    public List<ProductDTO> listProducts() {
//        ดึงรายการสินค้าทั้งหมดจากฐานข้อมูล
        List<Product> products = productRepository.findAll();
//        สร้างลิสต์ใหม่เพื่อเก็บข้อมูลสินค้าในรูปแบบของ ProductDTO
        List<ProductDTO> productDTOS = new ArrayList<>();
//        ใช้ลูป for เพื่อวนผ่านแต่ละ Product ในลิสต์ของ products.
        for(Product product : products){
//            แปลง Product เป็น ProductDTO โดยการเรียกใช้คอนสตรัคเตอร์ ProductDTO(product) และเพิ่มลงในลิสต์ productDTOS
            productDTOS.add(new ProductDTO(product));
        }
//        คืนค่าลิสต์ของ ProductDTO
        return productDTOS;
    }

    @Override
    public void updateProduct(Long productId, ProductDTO productDTO, Category category){
        Product product = getProductFromDTO(productDTO, category);

        product.setId(productId);

        productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotExistException{
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(!optionalProduct.isPresent()){
            throw new ProductNotExistException("Product id is invalid " + productId);
        }
        return optionalProduct.get();
    }


}
