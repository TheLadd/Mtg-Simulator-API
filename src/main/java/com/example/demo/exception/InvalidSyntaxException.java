package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidSyntaxException extends RuntimeException {
    //  Seems like I shouldn't need to tell it to use it's super-class' parameterized constructor, no?
    //  It doesn't seem to inherit it by default, despite my understanding of OOP...
    public InvalidSyntaxException(String msg) {
        super(msg);
    }
}