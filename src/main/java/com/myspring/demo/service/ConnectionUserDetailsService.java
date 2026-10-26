
package com.myspring.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspring.demo.user.Users;
import com.myspring.demo.user.UsersRepository;

@Service
public class ConnectionUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public ConnectionUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Users user = usersRepository.getOneUserWithEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found")
            );

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUserEmail())
            .password(user.getUserPassword())
            .roles("USER")
            .build();
    }
}
