package com.ecaj.dbankingbackend.account.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SA")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class SavingAccount extends BankAccount {
    private double interestRate;
}
