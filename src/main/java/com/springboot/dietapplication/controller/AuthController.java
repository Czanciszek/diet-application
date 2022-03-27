package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.config.JwtTokenUtil;
import com.springboot.dietapplication.config.KeyUtility;
import com.springboot.dietapplication.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader HttpHeaders httpHeaders) throws Exception {

        String[] decodedValues = decodeNewAuthorization(httpHeaders.get("Authorization"));

        authenticate(decodedValues[0], decodedValues[1]);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(decodedValues[0]);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    private String[] decodeAuthorization(List<String> auth) throws Exception {

        if (auth == null || auth.size() == 0) {
            throw new Exception("INVALID_CREDENTIALS");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(auth.get(0));
        return new String(decodedBytes).split(":");
    }

    private String[] decodeNewAuthorization(List<String> auth) throws Exception {

        if (auth == null || auth.size() == 0) {
            throw new Exception("INVALID_CREDENTIALS");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(auth.get(0));

        PrivateKey privateKey = KeyUtility.loadPrivateKey();

        String decryptedText = KeyUtility.doRSADecryption(decodedBytes, privateKey);

        return decryptedText.split(":");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}