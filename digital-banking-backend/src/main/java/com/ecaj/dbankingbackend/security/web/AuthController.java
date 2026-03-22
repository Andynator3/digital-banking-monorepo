package com.ecaj.dbankingbackend.security.web;

import com.ecaj.dbankingbackend.security.dtos.AuthResponseDTO;
import com.ecaj.dbankingbackend.security.dtos.LoginRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;

    // 1. Méthode Login
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String scope = String.join(" ", roles);
        Instant instant = Instant.now();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(60, ChronoUnit.MINUTES)) // Expiration dans 1h
                .subject(request.getUsername())
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet
        );
        String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

        return new AuthResponseDTO(jwt, request.getUsername(), roles);
    }

    // 2. Méthode Profile (Récupère les infos de l'utilisateur actuellement connecté)
    @GetMapping("/profile")
    public AuthResponseDTO profile(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // On ne renvoie pas de nouveau token ici, juste les infos de profil
        return new AuthResponseDTO(null, authentication.getName(), roles);
    }


}

