package com.ecaj.dbankingbackend.account.repositories;

import com.ecaj.dbankingbackend.account.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
