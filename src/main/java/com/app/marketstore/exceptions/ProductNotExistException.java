package com.app.marketstore.exceptions;

public class ProductNotExistException extends Exception{
    public ProductNotExistException(String msg){
        super(msg);
    }
}
