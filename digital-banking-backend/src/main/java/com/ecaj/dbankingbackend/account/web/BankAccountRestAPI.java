package com.ecaj.dbankingbackend.account.web;

import com.ecaj.dbankingbackend.account.dtos.BankAccountDTO;
import com.ecaj.dbankingbackend.account.exceptions.BankAccountNotFoundException;
import com.ecaj.dbankingbackend.account.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER')")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

}
