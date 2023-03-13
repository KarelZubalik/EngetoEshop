package com.example.EngetoEshop;

public class IdException extends Exception{
    public IdException(Long id){
        super("Produkt s id="+id+" nebyl nalezen.");
    }
}
