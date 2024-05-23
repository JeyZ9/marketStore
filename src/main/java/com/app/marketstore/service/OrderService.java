package com.app.marketstore.service;

import com.app.marketstore.dto.CheckoutItemDTO;
import com.app.marketstore.exceptions.OrderNotFoundException;
import com.app.marketstore.model.Order;
import com.app.marketstore.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;

public interface OrderService {
//    สร้างเซสชันการชำระเงินและโยนข้อยกเว้น SessionException หากเกิดข้อผิดพลาด
    Session createSession(List<CheckoutItemDTO> checkoutItemDTOList) throws StripeException;
//    วางคำสั่งซื้อสำหรับผู้ใช้ตามเซสชันการชำระเงินที่เกี่ยวข้อง
    void placeOrder(User user, String sessionId);
//    ดึงรายการคำสั่งซื้อของผู้ใช้
    List<Order> listOrder(User user);
//    ดึงคำสั่งซื้อตาม ID และโยนข้อยกเว้น OrderNotFoundException หากไม่พบคำสั่งซื้อ
    Order getOrder(Long id, User user) throws OrderNotFoundException;
}
