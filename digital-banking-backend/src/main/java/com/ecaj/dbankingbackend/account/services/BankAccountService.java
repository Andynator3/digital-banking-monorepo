package com.ecaj.dbankingbackend.account.services;

import com.ecaj.dbankingbackend.operation.dtos.AccountHistoryDTO;
import com.ecaj.dbankingbackend.account.dtos.BankAccountDTO;
import com.ecaj.dbankingbackend.account.dtos.CurrentBankAccountDTO;
import com.ecaj.dbankingbackend.account.dtos.SavingBankAccountDTO;
import com.ecaj.dbankingbackend.operation.exceptions.BalanceNotSufficientException;
import com.ecaj.dbankingbackend.account.exceptions.BankAccountNotFoundException;
import com.ecaj.dbankingbackend.customer.exceptions.CustomerNotFoundException;
import com.ecaj.dbankingbackend.operation.dtos.AccountOperationDTO;

import java.util.List;
public interface BankAccountService {
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    List<BankAccountDTO> bankAccountList();

}
