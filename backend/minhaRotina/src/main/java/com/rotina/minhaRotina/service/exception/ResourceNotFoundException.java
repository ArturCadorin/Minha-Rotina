package com.rotina.minhaRotina.service.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id){
        super(resource + " não encontrado com ID: " + id);
    }
}
