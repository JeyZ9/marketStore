package com.app.marketstore.repository;

import com.app.marketstore.model.Order;
import com.app.marketstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //    ค้นหาคำสั่งซื้อของผู้ใช้ทั้งหมดเรียงจากใหม่ไปเก่า
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
