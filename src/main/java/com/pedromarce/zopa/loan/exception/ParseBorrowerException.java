package com.pedromarce.zopa.loan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParseBorrowerException extends RuntimeException {

    private Throwable e;

}
