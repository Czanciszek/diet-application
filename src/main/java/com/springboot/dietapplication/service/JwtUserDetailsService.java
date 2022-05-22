package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.model.psql.user.UserEntity;
import com.springboot.dietapplication.repository.UserEntityRepository;
import com.springboot.dietapplication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository psqlUserRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User with provided name doesn't exist.");
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList
                (new SimpleGrantedAuthority(user.getUserType()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    public void registerNewUser(PsqlUser user) {
        if (psqlUserRepository.findByName(user.getName()) != null) {
            // TODO: Throw error that user with that name already exists
            return;
        }

        if (psqlUserRepository.findByName(user.getEmail()) != null) {
            // TODO: Throw error that user with that name already exists
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        psqlUserRepository.save(user);
    }

}
