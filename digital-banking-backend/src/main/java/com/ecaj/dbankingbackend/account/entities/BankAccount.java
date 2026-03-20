package com.ecaj.dbankingbackend.account.entities;

import com.ecaj.dbankingbackend.customer.entities.Customer;
import com.ecaj.dbankingbackend.operation.entities.AccountOperation;
import com.ecaj.dbankingbackend.account.enums.AccountStatus;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
