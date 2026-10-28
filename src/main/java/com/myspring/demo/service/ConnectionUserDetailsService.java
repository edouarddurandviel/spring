package com.myspring.demo.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspring.demo.security.ExtendedUserDetails;
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

             return new ExtendedUserDetails(
                user.getUserId(),
                user.getUserEmail(),
                user.getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
