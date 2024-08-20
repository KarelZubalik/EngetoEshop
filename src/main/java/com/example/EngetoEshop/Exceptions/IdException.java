package com.example.EngetoEshop.Exceptions;

public class IdException extends Exception{
    public IdException(Long id){
        super("Produkt s id="+id+" nebyl nalezen.");
    }
}
