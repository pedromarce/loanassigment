package com.pedromarce.zopa.loan.components;

import com.pedromarce.zopa.loan.dtos.Borrower;
import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.exception.NotBorrowerAvailableException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Loan {

    private static int MONTHS_LOAN = 36;
    private static float MONTHS_PERIOD_RATE = 12;

    private LoanRequest loanRequest;

    private void initialiseBorrowers() {
        final Comparator<Borrower> comparator = Comparator
                .comparing(Borrower::getRate);
        loanRequest.getBorrowers().sort(comparator);
    }

    public Double getMonthlyQuota(final LoanRequest request) {
        long amountBorrowed;
        long amountPending = request.getAmountRequested();
        initialiseBorrowers();
        Double monthlyQuota = 0.0;
        for (Borrower borrower : loanRequest.getBorrowers()) {
            amountBorrowed = amountPending <= borrower.getAmount() ? amountPending : borrower.getAmount();
            monthlyQuota += calculateMonthlyQuota(borrower.getRate(), amountBorrowed, request.getMonths());
            amountPending -= amountBorrowed;
            if (amountPending == 0) return monthlyQuota;
        }
        throw new NotBorrowerAvailableException();
    }

    protected Double calculateMonthlyQuota(final Double rate, final long amount, final float months) {
        return (amount * Math.pow(1.0 + (rate / MONTHS_PERIOD_RATE), months)) / months;
    }

}

