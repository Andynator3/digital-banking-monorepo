package com.ecaj.dbankingbackend.security.repositories;

import com.ecaj.dbankingbackend.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}

