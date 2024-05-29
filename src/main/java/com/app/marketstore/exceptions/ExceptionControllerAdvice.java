package com.app.marketstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//ระบุว่าคลาสนี้เป็นคลาสที่ให้คำแนะนำแก่คอนโทรลเลอร์ (Controller) ในแอปพลิเคชัน
@ControllerAdvice
public class ExceptionControllerAdvice {
//    ระบุเมธอดที่ใช้จัดการกับข้อยกเว้นที่ระบุในพารามิเตอร์ของ @ExceptionHandler แต่ละตัว
    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<String> handleUpdateFailException(CustomException customException){
        return new ResponseEntity<>(customException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProductNotExistException.class)
    public final ResponseEntity<String> handleUpdateFailException(ProductNotExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CartItemNotExistException.class)
    public final ResponseEntity<String> handleUpdateFailException(CartItemNotExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OrderNotFoundException.class)
    public final ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
