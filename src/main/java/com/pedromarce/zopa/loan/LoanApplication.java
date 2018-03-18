package com.pedromarce.zopa.loan;

import com.pedromarce.zopa.loan.exception.InitialisationException;
import com.pedromarce.zopa.loan.exception.NotBorrowerAvailableException;
import com.pedromarce.zopa.loan.services.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

import static java.lang.System.exit;

@AllArgsConstructor
@SpringBootApplication
public class LoanApplication implements CommandLineRunner {

    LoanService loanService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LoanApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        try {
            loanService.calculateLoan(loanService.requestCommandLine(Arrays.asList(args))).printResults();
        } catch (NotBorrowerAvailableException ex) {
            System.out.println("Unable to provide a quota at this time, no borrowers available");
        } catch (InitialisationException ex) {
            System.out.println("Unable to provide a quota at this time, internal error");
        }
        exit(0);
    }
}
