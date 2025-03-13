package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutException extends RuntimeException {
    public GatewayTimeoutException(String msg) {
        super(msg);
    }
}