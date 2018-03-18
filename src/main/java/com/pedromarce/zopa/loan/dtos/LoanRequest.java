package com.pedromarce.zopa.loan.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoanRequest {

    private String csvFilename;

    private long amountRequested;

    private float months;

    private List<Borrower> borrowers;

}
