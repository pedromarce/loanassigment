package com.pedromarce.zopa.loan.dtos;

import com.pedromarce.zopa.loan.exception.ParseBorrowerException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BorrowerTest {

    @Test(expected = ParseBorrowerException.class)
    public void ExceptionWhenTooManyColumns() {
        Borrower.fromCSV("one,0.01,10,100");
    }

    @Test(expected = ParseBorrowerException.class)
    public void ExceptionWhenTooFewColumns() {
        Borrower.fromCSV("one,0.01");
    }

    @Test(expected = ParseBorrowerException.class)
    public void ExceptionWhenWrongRate() {
        Borrower.fromCSV("one,rate,10");
    }

    @Test(expected = ParseBorrowerException.class)
    public void ExceptionWhenWrongAmount() {
        Borrower.fromCSV("one,0.01,amount");
    }

}