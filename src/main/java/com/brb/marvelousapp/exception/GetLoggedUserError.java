package com.brb.marvelousapp.exception;

public class GetLoggedUserError extends RuntimeException {

    public GetLoggedUserError(String exp) {
        super(exp);
    }
}
