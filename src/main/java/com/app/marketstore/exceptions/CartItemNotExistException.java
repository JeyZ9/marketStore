package com.app.marketstore.exceptions;

public class CartItemNotExistException extends Exception {
    public CartItemNotExistException(String msg){
        super(msg);
    }
}
