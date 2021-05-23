package com.inventi.entrytask.controller;

import com.inventi.entrytask.CSVhelper.CSVHelper;
import com.inventi.entrytask.repository.AccountTransactionRepository;
import com.inventi.entrytask.service.AccountTransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/transaction")
public class AccountTransactionController {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionRepository accountTransactionRepository, AccountTransactionService accountTransactionService) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountTransactionService = accountTransactionService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                accountTransactionService.save(file);
                return "Succes";
            } catch (Exception e) {
                return e.toString();
            }
        }
        return "Bbzm";
    }

    @GetMapping("/export")
    public void exportCsvFile(@RequestBody(required = false) DateFromToRequest dateFromToRequest, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=exported.csv");
        accountTransactionService.exportCsvFile(response.getWriter(), dateFromToRequest);
    }

    @GetMapping("/export/{accountNumber}")
    public void exportCsvFile(@PathVariable String accountNumber, @RequestBody(required = false) DateFromToRequest dateFromToRequest, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=exported.csv");
        accountTransactionService.exportCsvFileForAccount(response.getWriter(), dateFromToRequest, accountNumber);
    }


}
