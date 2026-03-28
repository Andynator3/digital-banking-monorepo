package com.ecaj.dbankingbackend.security.services.impl;

import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.services.AuthSecurityService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AuthSecurityService authSecurityService;

    public UserDetailsServiceImpl(AuthSecurityService authSecurityService) {
        this.authSecurityService = authSecurityService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = authSecurityService.getUserByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

       String[] roles = appUser.getRoles().stream().map(au ->au.getRoleName()).toArray(String[]::new);
       UserDetails userDetails = User
               .withUsername(appUser.getUsername())
               .password(appUser.getPassword())
               .roles(roles)
               .build();
       return userDetails;
    }
    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = authSecurityService.getUserByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException(String.format("User %s not found", username));

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .toList()
        );
    }*/
}

