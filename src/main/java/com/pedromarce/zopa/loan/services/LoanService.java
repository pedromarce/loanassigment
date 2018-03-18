package com.pedromarce.zopa.loan.services;

import com.pedromarce.zopa.loan.components.Loan;
import com.pedromarce.zopa.loan.dtos.Borrower;
import com.pedromarce.zopa.loan.dtos.LoanRequest;
import com.pedromarce.zopa.loan.dtos.LoanResult;
import com.pedromarce.zopa.loan.exception.InitialisationException;
import com.pedromarce.zopa.loan.exception.ParseBorrowerException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    private static int MONTHS_LOAN = 36;
    private static int MONTHS_PERIOD_RATE = 12;
    private static int MINIMUM_LOAN_AMOUNT = 1000;
    private static int MAXIMUM_LOAN_AMOUNT = 15000;

    private boolean headerProcessed;

    public LoanRequest requestCommandLine(List<String> arguments) {

        if (arguments.size() != 2) throw new InitialisationException("Wrong number of arguments", null);

        List<Borrower> borrowers = validateFile(arguments.get(0));

        try {
            validateAmount(Integer.parseInt(arguments.get(1)));
        } catch (NumberFormatException e) {
            logger.debug("Bad formatted amount passed %s", arguments.get(1));
            throw new InitialisationException("Wrong format for arguments", e);
        }
        return LoanRequest.builder()
                .borrowers(borrowers)
                .amountRequested(Integer.parseInt(arguments.get(1)))
                .months(MONTHS_LOAN)
                .build();
    }

    public LoanResult calculateLoan(LoanRequest loanRequest) {

        Loan loan = Loan.builder().loanRequest(loanRequest).build();

        Double monthlyQuota = loan.getMonthlyQuota(loanRequest);

        return LoanResult.builder()
                .loanRequest(loanRequest)
                .montlyQuota(monthlyQuota)
                .rate(calculateRate(loanRequest, monthlyQuota))
                .build();

    }

    private List<Borrower> validateFile(final String csvFileName) {
        headerProcessed = false;
        try (Stream<String> stream = Files.lines(Paths.get(csvFileName))) {
            return stream.map(this::parseBorrower)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new InitialisationException("File is not Existent", e);
        }
    }

    private void validateAmount(final int amount) {
        if (amount < MINIMUM_LOAN_AMOUNT || amount > MAXIMUM_LOAN_AMOUNT || amount % 100 != 0)
            throw new InitialisationException("Amount is Not Valid", null);
    }

    private Borrower parseBorrower(String csvLine) {
        if (!headerProcessed) {
            if (!csvLine.equals("Lender,Rate,Available"))
                throw new InitialisationException("File is Bad Formatted", null);
            headerProcessed = true;
        } else {
            try {
                return Borrower.fromCSV(csvLine);
            } catch (ParseBorrowerException e) {
                throw new InitialisationException("File is Bad Formatted", null);
            }
        }
        return null;
    }

    private Double calculateRate(final LoanRequest loanRequest, final Double monthlyQuota) {
        return MONTHS_PERIOD_RATE * (Math.pow((monthlyQuota * loanRequest.getMonths()) / loanRequest.getAmountRequested(), 1 / loanRequest.getMonths()) - 1.0);
    }

}
