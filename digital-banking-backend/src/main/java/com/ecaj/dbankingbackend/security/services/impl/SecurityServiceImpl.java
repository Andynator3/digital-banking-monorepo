package com.ecaj.dbankingbackend.security.services.impl;

import com.ecaj.dbankingbackend.security.entities.AppRole;
import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.repositories.AppRoleRepository;
import com.ecaj.dbankingbackend.security.repositories.AppUserRepository;
import com.ecaj.dbankingbackend.security.services.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email) {
        log.info("Création d'un nouvel utilisateur");
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password)); // Le mot de passe DOIT être haché
        appUser.setEmail(email);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String roleName) {
        log.info("Création d'un nouveau rôle");
        return appRoleRepository.save(new AppRole(null, roleName));
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Attribution du rôle {} à l'utilisateur {}", roleName, username);
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}

