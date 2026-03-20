package com.ecaj.dbankingbackend.operation.services;

import com.ecaj.dbankingbackend.account.entities.BankAccount;
import com.ecaj.dbankingbackend.account.exceptions.BankAccountNotFoundException;
import com.ecaj.dbankingbackend.account.repositories.BankAccountRepository;
import com.ecaj.dbankingbackend.operation.dtos.AccountHistoryDTO;
import com.ecaj.dbankingbackend.operation.dtos.AccountOperationDTO;
import com.ecaj.dbankingbackend.operation.entities.AccountOperation;
import com.ecaj.dbankingbackend.operation.entities.Credit;
import com.ecaj.dbankingbackend.operation.entities.Debit;
import com.ecaj.dbankingbackend.operation.enums.OperationType;
import com.ecaj.dbankingbackend.operation.exceptions.BalanceNotSufficientException;
import com.ecaj.dbankingbackend.operation.mappers.OperationMapperImpl;
import com.ecaj.dbankingbackend.operation.repositories.AccountOperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService{
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private OperationMapperImpl operationMapper;


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        Debit debit = new Debit();
        debit.setType(OperationType.DEBIT);
        debit.setAmount(amount);
        debit.setDescription(description);
        debit.setOperationDate(new Date());
        debit.setBankAccount(bankAccount);
        accountOperationRepository.save(debit);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        Credit credit = new Credit();
        credit.setType(OperationType.CREDIT);
        credit.setAmount(amount);
        credit.setDescription(description);
        credit.setOperationDate(new Date());
        credit.setBankAccount(bankAccount);
        accountOperationRepository.save(credit);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> operationMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->operationMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

}
