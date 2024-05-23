package com.app.marketstore.service;

import com.app.marketstore.dto.users.SigninDTO;
import com.app.marketstore.dto.users.SigninResponseDTO;
import com.app.marketstore.dto.users.SignupDTO;
import com.app.marketstore.dto.users.SignupResponseDTO;
import com.app.marketstore.exceptions.AuthenticationFailException;
import com.app.marketstore.exceptions.CustomException;

public interface UserService {

    //    เมดทอดสมัครใช้งานกรณีที่มีข้อผิดพลาดในการลงทะเบียนจะแสดงข้อผิดพลาดจากคลาส CustomException และส่งค่าคืนจากคลาส SignupResponseDTO
    SignupResponseDTO signup(SignupDTO signupDTO) throws CustomException;

    //    เมดทอดล็อคอินกรณีที่มีข้อผิดพลาดในการล็อคอินจะแสดงข้อผิดพลาดจากคลาส AuthenticationFailException และส่งค่าคืนจากคลาส SigninResponseDTO
    SigninResponseDTO signin(SigninDTO signinDTO) throws AuthenticationFailException, CustomException;
}
