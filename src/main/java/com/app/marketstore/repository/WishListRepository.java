package com.app.marketstore.repository;

import com.app.marketstore.model.User;
import com.app.marketstore.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    //    ค้นหาสินค้าที่ชื่นชอบของผู้ใช้ทั้งหมดเรียงจากใหม่ไปเก่า
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
