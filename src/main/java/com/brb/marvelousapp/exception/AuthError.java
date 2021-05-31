package com.brb.marvelousapp.exception;

public class AuthError extends RuntimeException {

    public AuthError(String exp) {
        super(exp);
    }

}
