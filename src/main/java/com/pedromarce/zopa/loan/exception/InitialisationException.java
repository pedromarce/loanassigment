package com.pedromarce.zopa.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InitialisationException extends RuntimeException {

    private String message;

    private Throwable e;

}
