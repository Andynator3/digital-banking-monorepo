package com.ecaj.dbankingbackend.security.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(unique = true)
    private  String confirmPassword;
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER car on a toujours besoin des rôles au moment de l'authentification
    private Collection<AppRole> roles = new ArrayList<>();

}
