package com.ecaj.dbankingbackend.security.services.impl;

import com.ecaj.dbankingbackend.security.entities.AppRole;
import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.repositories.AppRoleRepository;
import com.ecaj.dbankingbackend.security.repositories.AppUserRepository;
import com.ecaj.dbankingbackend.security.services.AuthSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class AuthSecurityServiceImpl implements AuthSecurityService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthSecurityServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


 /*   @Override
    public AppUser addNewUser(AppUser appUser) {
        log.info("Création d'un nouvel utilisateur");
        // Cripter le password de l'utilisateur
        String hashedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(hashedPassword);
        return appUserRepository.save(appUser);
    }*/

    /*@Override
    public AppRole addNewRole(AppRole appRole) {
        log.info("Création d'un nouveau rôle");
        return appRoleRepository.save(appRole);
    }*/

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        log.info("Création d'un nouvel utilisateur");
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("This user i already exist");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String roleName) {
        log.info("Création d'un nouveau rôle");
        AppRole appRole = appRoleRepository.findById(roleName).orElse(null);
        if (appRole != null) throw new RuntimeException("This role i already exist");
        appRole = AppRole.builder()
                .roleName(roleName)
                .build();
        AppRole savedRole = appRoleRepository.save(appRole);
        return savedRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Attribution du rôle {} à l'utilisateur {}", roleName, username);
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(roleName).get();
        appUser.getRoles().add(appRole);
       // appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        log.info("Suppression du rôle {} à l'utilisateur {}", roleName, username);
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(roleName).get();
        appUser.getRoles().remove(appRole);
        // appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> appUsersList() {
        return appUserRepository.findAll();
    }

}

