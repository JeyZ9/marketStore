package com.app.marketstore.service;

import com.app.marketstore.model.User;
import com.app.marketstore.model.WishList;

import java.util.List;

public interface WishListService {
//    สร้างและบันทึกรายการสินค้าที่ต้องการ
    void createWishList(WishList wishList);

//    ดึงรายการสินค้าที่ต้องการของผู้ใช้และคืนค่าในรูปแบบของลิสต์
    List<WishList> readWishList(User user);
}
