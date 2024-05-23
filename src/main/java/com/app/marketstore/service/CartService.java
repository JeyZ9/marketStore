package com.app.marketstore.service;

import com.app.marketstore.dto.AddToCartDTO;
import com.app.marketstore.dto.CartDTO;
import com.app.marketstore.exceptions.CartItemNotExistException;
import com.app.marketstore.model.Product;
import com.app.marketstore.model.User;

public interface CartService {
//    เพิ่มสินค้าลงในตะกร้าสินค้าของผู้ใช้
    void addToCart(AddToCartDTO addToCartDTO, Product product, User user);
//    ดึงรายการสินค้าที่อยู่ในตะกร้าของผู้ใช้
    CartDTO listCartItems(User user);
//    ลบรายการสินค้าออกจากตะกร้าของผู้ใช้ และถ้ารายการสินค้าที่ต้องการลบไม่อยู่ในตะกร้าของผู้ใช้จะแสดงข้อผิดพลาด
    void deleteCartItem(Long cartItemId, User user) throws CartItemNotExistException;
}
