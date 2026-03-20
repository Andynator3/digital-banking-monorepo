package com.ecaj.dbankingbackend.operation.services;

import com.ecaj.dbankingbackend.account.exceptions.BankAccountNotFoundException;
import com.ecaj.dbankingbackend.operation.dtos.AccountHistoryDTO;
import com.ecaj.dbankingbackend.operation.dtos.AccountOperationDTO;
import com.ecaj.dbankingbackend.operation.exceptions.BalanceNotSufficientException;

import java.util.List;

public interface OperationService {
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
    List<AccountOperationDTO> accountHistory(String accountId);
}
