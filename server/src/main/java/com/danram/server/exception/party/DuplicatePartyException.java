package com.danram.server.exception.party;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatePartyException extends RuntimeException {
    private String message;

    public DuplicatePartyException(String name) {
        super(name);
        message = name;
    }
}
