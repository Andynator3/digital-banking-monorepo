package com.ecaj.dbankingbackend.operation.entities;

import com.ecaj.dbankingbackend.account.entities.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ecaj.dbankingbackend.operation.enums.OperationType;

import jakarta.persistence.*;
import java.util.Date;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 10, discriminatorType = DiscriminatorType.STRING )
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", insertable = false, updatable = false)
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
}

