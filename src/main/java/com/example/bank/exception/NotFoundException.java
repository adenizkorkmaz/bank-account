package com.example.bank.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private Long entityId;

    public NotFoundException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }


}
