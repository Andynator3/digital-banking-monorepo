package com.ecaj.dbankingbackend.security.repositories;

import com.ecaj.dbankingbackend.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}

