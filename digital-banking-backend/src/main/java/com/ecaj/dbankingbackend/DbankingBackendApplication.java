package com.ecaj.dbankingbackend;

import com.ecaj.dbankingbackend.account.dtos.BankAccountDTO;
import com.ecaj.dbankingbackend.account.dtos.CurrentBankAccountDTO;
import com.ecaj.dbankingbackend.customer.dtos.CustomerDTO;
import com.ecaj.dbankingbackend.account.dtos.SavingBankAccountDTO;
import com.ecaj.dbankingbackend.operation.entities.AccountOperation;
import com.ecaj.dbankingbackend.account.entities.CurrentAccount;
import com.ecaj.dbankingbackend.customer.entities.Customer;
import com.ecaj.dbankingbackend.account.entities.SavingAccount;
import com.ecaj.dbankingbackend.account.enums.AccountStatus;
import com.ecaj.dbankingbackend.operation.enums.OperationType;
import com.ecaj.dbankingbackend.customer.exceptions.CustomerNotFoundException;
import com.ecaj.dbankingbackend.operation.repositories.AccountOperationRepository;
import com.ecaj.dbankingbackend.account.repositories.BankAccountRepository;
import com.ecaj.dbankingbackend.customer.repositories.CustomerRepository;
import com.ecaj.dbankingbackend.account.services.BankAccountService;
import com.ecaj.dbankingbackend.customer.services.CustomerService;
import com.ecaj.dbankingbackend.operation.services.OperationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbankingBackendApplication.class, args);
    }
   // @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService, BankAccountService bankAccountService, OperationService operationService){
        return args -> {
            // Create a customer list
           Stream.of("Pascal","Aline","Pierre").forEach(name->{
               CustomerDTO customer=new CustomerDTO();
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               customerService.saveCustomer(customer);
           });
            // Create two bank accounts for each customer
            customerService.listCustomers().forEach(customer->{
               try {
                   bankAccountService.saveCurrentBankAccount(Math.random()*90000,500,customer.getId());
                   bankAccountService.saveSavingBankAccount(Math.random()*120000,3.5,customer.getId());

               } catch (CustomerNotFoundException e) {
                   e.printStackTrace();
               }
           });
            // Add transactions for each account
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    } else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    operationService.credit(accountId,10000+Math.random()*120000,"Credit");
                    operationService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Pascal","Aline","Pierre").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
           /* bankAccountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });*/
        };

    }

}
