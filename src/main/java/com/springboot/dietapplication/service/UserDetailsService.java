package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    UserRepository psqlUserRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        PsqlUser user = psqlUserRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User with provided name doesn't exist.");
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList
                (new SimpleGrantedAuthority(user.getUserType()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

}
