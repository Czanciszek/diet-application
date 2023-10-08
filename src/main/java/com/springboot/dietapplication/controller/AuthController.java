package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.config.JwtTokenUtil;
import com.springboot.dietapplication.config.KeyUtility;
import com.springboot.dietapplication.config.SpringSecurityConfig;
import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.model.psql.user.PsqlUserType;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.model.type.LoginResult;
import com.springboot.dietapplication.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    SpringSecurityConfig springSecurityConfig;

    @GetMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader HttpHeaders httpHeaders) {

        final String token;
        try {
            String[] decodedValues = decodeAuthorization(httpHeaders.get("AuthEncrypt"));
            authenticate(decodedValues);

            final UserDetails userDetails = springSecurityConfig.jwtUserDetailsService().loadUserByUsername(decodedValues[0]);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(new LoginResult(token));
    }

    @PostMapping(path = "/register", produces = "application/json")
    public ResponseEntity<PsqlUser> registerUser(@RequestBody UserEntity userEntity) {

        PsqlUser user = new PsqlUser(userEntity);

        PsqlUserType userType = userTypeRepository.findByName(userEntity.getUserType().toUpperCase());
        user.setUserTypeId(userType.getId());

        springSecurityConfig.jwtUserDetailsService().registerNewUser(user);

        return ResponseEntity.ok(user);
    }

    private String[] decodeAuthorization(List<String> auth) throws Exception {

        if (auth == null || auth.size() == 0) {
            throw new Exception("INVALID_CREDENTIALS");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(auth.get(0));

        PrivateKey privateKey = KeyUtility.loadPrivateKey();

        String decryptedText = KeyUtility.doRSADecryption(decodedBytes, privateKey);

        return decryptedText.split(":");
    }

    private void authenticate(String[] decodedValues) throws Exception {
        try {
            if (decodedValues.length != 2) {
                throw new Exception("INVALID_CREDENTIALS");
            }

            springSecurityConfig.authenticationProvider().authenticate(new UsernamePasswordAuthenticationToken(decodedValues[0], decodedValues[1]));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}