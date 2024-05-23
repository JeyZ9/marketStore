package com.app.marketstore.service;

import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.model.AuthenticationToken;
import com.app.marketstore.model.User;

public interface AuthenticationService {

//    บันทึกโทเค็นการยืนยันตัวตน
    void saveConfirmationToken(AuthenticationToken authenticationToken);
//    ดึงโทเค็นการยืนยันตัวตนที่เกี่ยวข้องกับผู้ใช้
    AuthenticationToken getToken(User user);
//    ดึงข้อมูลผู้ใช้จากโทเค็น
    User getUser(String token);
//    ตรวจสอบโทเค็นการยืนยันตัวตนและแสดงข้อผิดพลาดเมื่อตรวจสอบล้มเหลว
    void authenticate(String token) throws AuthenticationFailException;
}
