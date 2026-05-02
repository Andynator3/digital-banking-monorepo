package com.ecaj.dbankingbackend.security.web;

import com.ecaj.dbankingbackend.security.dtos.AuthResponseDTO;
import com.ecaj.dbankingbackend.security.dtos.LoginRequestDTO;

import com.ecaj.dbankingbackend.security.entities.AppUser;
import com.ecaj.dbankingbackend.security.services.AuthSecurityService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthSecurityController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthSecurityService authSecurityService;

    @PostMapping("/login")
   // public Map<String, String> login(@RequestBody LoginRequestDTO loginRequestDTO)
    public AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 1. On authentifie l'utilisateur via les informations du DTO
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        // 2. On récupère dynamiquement tous ses rôles (ex: "SCOPE_USER", "SCOPE_ADMIN")
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();

        // 3. On construit le token JWT avec les VRAIES informations
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        // On force l'algorithme HS512 dans l'en-tête du token
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS512).build();

        // On passe le Header ET le ClaimsSet à l'encodeur
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)).getTokenValue();

        // 4. On encode le token
        //String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        // 5. On le retourne via un AuthResponseDTO
        //return Map.of("access-token", jwtAccessToken);
        return new AuthResponseDTO(jwtAccessToken);
    }

/*

    @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return authSecurityService.addNewUser(appUser);
    }
    @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return authSecurityService.addNewRole(appRole);
    }

    @PostMapping("/addRoleToUser")
    public void assignRoleToUser(@RequestBody AppRole appRole){
        authSecurityService.addRoleToUser(appRole);
    }
*/

    @GetMapping("/users")
    public List<AppUser> getAppUsers(){
        return authSecurityService.appUsersList();
    }
}

