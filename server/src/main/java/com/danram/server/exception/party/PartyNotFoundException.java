package com.danram.server.exception.party;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PartyNotFoundException extends RuntimeException {
    private String message;

    public PartyNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}
