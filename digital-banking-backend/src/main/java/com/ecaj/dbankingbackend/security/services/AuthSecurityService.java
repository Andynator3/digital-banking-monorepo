package com.ecaj.dbankingbackend.security.services;

import com.ecaj.dbankingbackend.security.entities.AppRole;
import com.ecaj.dbankingbackend.security.entities.AppUser;

import java.util.List;

public interface AuthSecurityService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String roleName);
    void addRoleToUser(String username, String roleName);

    void removeRoleFromUser(String username, String roleName);
    AppUser getUserByUsername(String username);
    List<AppUser> appUsersList();
}

