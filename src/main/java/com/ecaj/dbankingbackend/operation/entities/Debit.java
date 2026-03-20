package com.ecaj.dbankingbackend.operation.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("DEBIT")
@Getter
@Setter
@NoArgsConstructor
public class Debit extends AccountOperation {
}
