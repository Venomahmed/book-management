package com.ness.bookmanagement.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Map<String, UserDetails> USER_REPOSITORY = new HashMap<>();

    @PostConstruct
    public void initUsers() {
        Stream.of(
                User.builder()
                        .username("istiak")
                        .password("password")
                        .authorities(new ArrayList<>())
                        .build(),

                User.builder()
                        .username("user1")
                            .password("pwd1")
                        .authorities(new ArrayList<>())
                        .build(),

                User.builder()
                        .username("user2")
                        .password("pwd2")
                        .authorities(new ArrayList<>())
                        .build(),

                User.builder()
                        .username("user3")
                        .password("pwd3")
                        .authorities(new ArrayList<>())
                        .build()
        ).forEach(user -> USER_REPOSITORY.put(user.getUsername(), user));


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = USER_REPOSITORY.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found " + username);
        }
        return user;
    }
}