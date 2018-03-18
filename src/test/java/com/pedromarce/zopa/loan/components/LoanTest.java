package com.pedromarce.zopa.loan.components;

import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.exception.NotBorrowerAvailableException;
import com.pedromarce.zopa.loan.services.LoanService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LoanTest {

    private LoanService loanService = new LoanService();

    private Loan loan = new Loan(LoanRequest.builder().amountRequested(15000).build());

    @Test(expected = NotBorrowerAvailableException.class)
    public void exceptionLargeAmount() {
        LoanRequest loanRequest = loanService.requestCommandLine(Arrays.asList("src/test/resources/testdata.csv", "15000"));

        loanService.calculateLoan(loanRequest);
    }

    @Test
    public void calculateMonthyQuota() {
        Double quota = loan.calculateMonthlyQuota(0.01, 1000, 36);
        Assert.assertEquals(28.62, quota, 0.009);
    }

}
