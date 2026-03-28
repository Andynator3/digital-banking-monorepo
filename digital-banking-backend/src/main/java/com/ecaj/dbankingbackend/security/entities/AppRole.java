package com.ecaj.dbankingbackend.security.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRole {
    @Id
    private String roleName;
}
