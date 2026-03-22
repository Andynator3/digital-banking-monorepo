package com.ecaj.dbankingbackend.security.services;

import com.ecaj.dbankingbackend.security.entities.AppRole;
import com.ecaj.dbankingbackend.security.entities.AppUser;

public interface SecurityService {
    AppUser addNewUser(String username, String password, String email);
    AppRole addNewRole(String roleName);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
}

