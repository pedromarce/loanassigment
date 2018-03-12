package com.pedromarce.zopa.loan.services;

import com.pedromarce.zopa.loan.components.Loan;
import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.exception.InitialisationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LoanServiceTest {

    private LoanService loanService = new LoanService();

    @Test
    public void calculateLoan() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","1000"));

        loanService.calculateLoan(loanRequest).printResults();
    }

    @Test
    public void LoanRequestIsCreated() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv","10000"));
        Assert.assertNotNull("Request is created ",loanRequest);
        Assert.assertEquals(10000,loanRequest.getAmountRequested());
        Assert.assertEquals(36,loanRequest.getMonths());
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