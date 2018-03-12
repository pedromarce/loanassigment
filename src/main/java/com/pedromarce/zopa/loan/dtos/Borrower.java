package com.pedromarce.zopa.loan.dtos;

import com.pedromarce.zopa.loan.LoanApplication;
import com.pedromarce.zopa.loan.exception.ParseBorrowerException;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class Borrower {

    private static final Logger logger = LoggerFactory.getLogger(Borrower.class);

    private String name;

    private Double rate;

    private Long amount;

    private Long available;

    public static Borrower fromCSV(@NotNull final String csvValue) throws ParseBorrowerException {
        try {
            String[] values = csvValue.split(",");
            if (values.length != 3) throw new ParseBorrowerException(null);
            return Borrower.builder()
                    .name(values[0])
                    .rate(Double.valueOf(values[1]))
                    .amount(Long.valueOf(values[2]))
                    .available(Long.valueOf(values[2]))
                    .build();
        } catch (NumberFormatException e) {
            logger.debug("Bad formatted borrower : %s", csvValue, e);
            throw new ParseBorrowerException(e);
        }
    }


}
