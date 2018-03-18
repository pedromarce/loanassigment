package com.pedromarce.zopa.loan.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoanResult {

    LoanRequest loanRequest;

    Double montlyQuota;

    Double rate;

    public void printResults() {
        System.out.println("Amount :" + loanRequest.getAmountRequested());
        System.out.println("Rate " + String.format("%1.1f", rate * 100));
        System.out.println("Quota " +  String.format("%6.2f", montlyQuota));
        System.out.println("Total " + String.format("%6.2f", montlyQuota * loanRequest.getMonths()));
    }
}
