package com.ecaj.dbankingbackend.operation.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("CREDIT")
@Getter
@Setter
@NoArgsConstructor
public class Credit extends AccountOperation{
}
