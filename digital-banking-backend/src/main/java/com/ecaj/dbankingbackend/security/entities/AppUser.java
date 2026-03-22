package com.ecaj.dbankingbackend.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER car on a toujours besoin des rôles au moment de l'authentification
    private List<AppRole> roles = new ArrayList<>();

}
