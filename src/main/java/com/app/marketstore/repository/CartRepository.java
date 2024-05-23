package com.app.marketstore.repository;

import com.app.marketstore.model.Cart;
import com.app.marketstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    //    ค้นหาสินค้าในตะกร้าของผู้ใช้ทั้งหมดเรียงจากใหม่ไปเก่า
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
