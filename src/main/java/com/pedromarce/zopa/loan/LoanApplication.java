package com.pedromarce.zopa.loan;

import com.pedromarce.zopa.loan.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class LoanApplication implements CommandLineRunner {

    @Autowired
    LoanService loanService;

    public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        loanService.calculateLoan(loanService.requestCommandLine(Arrays.asList(args))).printResults();
    }
}
