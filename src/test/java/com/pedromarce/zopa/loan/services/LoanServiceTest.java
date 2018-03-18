package com.pedromarce.zopa.loan.services;

import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.dtos.LoanResult;
import com.pedromarce.zopa.loan.exception.InitialisationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LoanServiceTest {

    private LoanService loanService = new LoanService();

    @Test
    public void calculateLoanSingleBorrower() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testbasicdata.csv", "1000"));

        LoanResult loanResult = loanService.calculateLoan(loanRequest);
        Assert.assertEquals(28.62, loanResult.getMontlyQuota(), 0.009);
        Assert.assertEquals(0.01, loanResult.getRate(), 0.001);


    }

    @Test
    public void calculateLoanMultipleBorrower() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testbasicdata.csv", "2000"));

        LoanResult loanResult = loanService.calculateLoan(loanRequest);
        Assert.assertEquals(58.11, loanResult.getMontlyQuota(), 0.01);
        Assert.assertEquals(0.015, loanResult.getRate(), 0.001);


    }

    @Test
    public void LoanRequestIsCreated() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","10000"));
        Assert.assertNotNull("Request is created ",loanRequest);
        Assert.assertEquals(10000,loanRequest.getAmountRequested());
        Assert.assertEquals(36, loanRequest.getMonths(), 0.0);
        Assert.assertEquals(7, loanRequest.getBorrowers().size());
    }

    @Test
    public void ExceptionWhenFileDoesNotExist() {
        try {
            loanService.requestCommandLine(Arrays.asList("nonExistent","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is not Existent", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenBadFormattedHeaderCSV() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/badformat/badformatheader.csv","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is Bad Formatted", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenBadFormattedAmountCSV() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/badformat/badformatamount.csv","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is Bad Formatted", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenBadFormattedRateCSV() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/badformat/badformatrate.csv","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is Bad Formatted", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenBadFormattedTooManyColumnsCSV() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/badformat/badformatmorecolumns.csv","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is Bad Formatted", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenBadFormattedTooFewColumnsCSV() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/badformat/badformatlesscolumns.csv","10000"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("File is Bad Formatted", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenLessThanMinimum() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","900"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("Amount is Not Valid", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenMoreThanMaximum() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","15100"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("Amount is Not Valid", e.getMessage());
        }
    }

    @Test
    public void ExceptionWhenWrongMultiple() {
        try {
            loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","1001"));
            Assert.fail("Exception expected");
        } catch (InitialisationException e) {
            Assert.assertEquals("Amount is Not Valid", e.getMessage());
        }
    }
}