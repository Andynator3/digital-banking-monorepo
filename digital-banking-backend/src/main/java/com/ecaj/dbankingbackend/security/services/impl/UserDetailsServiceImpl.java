package com.ecaj.dbankingbackend.security.services.impl;

import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.services.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = securityService.loadUserByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException("Utilisateur introuvable");

        Collection<GrantedAuthority> authorities = appUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        // On retourne l'objet User de Spring Security (pas notre AppUser)
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}

