package com.pedromarce.zopa.loan.components;

import com.pedromarce.zopa.loan.dtos.Borrower;
import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.exception.NotBorrowerAvailableException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Component
@Getter
@Setter
@Builder
public class Loan {

    private LoanRequest loanRequest;

    private long amountAvailable;

    public boolean validateAmount(long amountRequested) {
        return amountAvailable >= amountRequested;
    }

    private void initialiseBorrowers() {
        final Comparator<Borrower> comparator = Comparator
                .comparing(Borrower::getRate);
        loanRequest.getBorrowers().sort(comparator);
        this.amountAvailable = loanRequest.getBorrowers().stream()
                .map(Borrower::getAmount)
                .reduce(0L, (a, b) -> a + b);
    }

    public Double getMonthlyQuota(final LoanRequest request) {
        long amountBorrowed,
                amountPending = request.getAmountRequested();
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

    private Double calculateMonthlyQuota(final Double rate, final long amount, final long months) {
        return (amount * Math.pow(1.0 + (rate / months), months)) / months;
    }

}

