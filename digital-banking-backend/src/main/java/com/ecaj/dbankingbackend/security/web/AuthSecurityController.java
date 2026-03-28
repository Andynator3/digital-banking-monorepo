package com.ecaj.dbankingbackend.security.web;

import com.ecaj.dbankingbackend.security.dtos.LoginRequestDTO;
import com.ecaj.dbankingbackend.security.entities.AppRole;
import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.services.AuthSecurityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthSecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    //private final AuthSecurityService authSecurityService;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    // 1. Méthode Login
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream().map(aut -> aut.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(60, ChronoUnit.MINUTES)) // Expiration dans 1h
                .subject(loginRequestDTO.getUsername())
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet
        );
        String jwtToken = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token", jwtToken);
    }

    /*@GetMapping("/users")
    public List<AppUser> getAppUsers(){
        return authSecurityService.appUsersList();
    }*/

   /* @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return authSecurityService.addNewUser(appUser);
    }*/
   /* @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return authSecurityService.addNewRole(appRole);
    }*/

   /* @PostMapping("/addRoleToUser")
    public void assignRoleToUser(@RequestBody AppRole appRole){
        authSecurityService.addRoleToUser(appRole);
    }
    */
}

