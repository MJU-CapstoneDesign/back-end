package com.danram.server.exception;

import com.danram.server.exception.member.MemberNotFoundException;
import com.danram.server.exception.party.DuplicatePartyException;
import com.danram.server.exception.party.PartyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(PartyNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PartyNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0001","Party is not found : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(MemberNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0002","Member is not found : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatePartyException.class)
    public ResponseEntity<ApiErrorResponse> handleException(DuplicatePartyException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0003","party name is duplicated : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
