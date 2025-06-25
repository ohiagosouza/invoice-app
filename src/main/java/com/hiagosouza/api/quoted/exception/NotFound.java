package com.hiagosouza.api.quoted.exception;

import org.webjars.NotFoundException;

public class NotFound extends NotFoundException {
    public NotFound(String message, String parameter) {
        super(message + parameter);
    }
}
