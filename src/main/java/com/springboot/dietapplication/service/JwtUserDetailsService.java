package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.user.MongoUserEntity;
import com.springboot.dietapplication.model.mongo.user.UserEntity;
import com.springboot.dietapplication.repository.MongoUserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    MongoUserEntityRepository userEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        MongoUserEntity user = userEntityRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User with provided name doesn't exist.");
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList
                (new SimpleGrantedAuthority(user.getUserType()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

    public void registerNewUser(MongoUserEntity user) {
        if (userEntityRepository.findByName(user.getName()) != null) {
            // TODO: Throw error that user with that name already exists
            return;
        }

        if (userEntityRepository.findByName(user.getEmail()) != null) {
            // TODO: Throw error that user with that name already exists
            return;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.save(user);
    }

    public UserEntity getCurrentUser() throws UsernameNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            MongoUserEntity user = userEntityRepository.findByName(username);
            if (user == null) {
                throw new UsernameNotFoundException("User with provided name doesn't exist.");
            }
            return new UserEntity(user);
        }

        throw new UsernameNotFoundException("Error getting current user authentication principal");
    }

}
