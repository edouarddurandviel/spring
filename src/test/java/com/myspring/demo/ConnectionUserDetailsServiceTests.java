package com.myspring.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// import service
import com.myspring.demo.service.ConnectionUserDetailsService;
import com.myspring.demo.user.Users;
import com.myspring.demo.user.UsersRepository;

@ExtendWith(MockitoExtension.class)

@DisplayName("ConnectionUserDetailsService") // describe
public class ConnectionUserDetailsServiceTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private ConnectionUserDetailsService userDetailsService;



    // test methods
    @Nested
    @DisplayName("loadUserByUsername()")
    class LoadUserByUsernameTests {

        @Test
        @DisplayName("Success")
        void shouldLoadUserByEmail() {

            Users userMock = mock(Users.class);

            when(userMock.getUserEmail())
                .thenReturn("mail@mail.com");

            when(userMock.getUserPassword())
                .thenReturn("encodedPassword");


            when(usersRepository.getOneUserWithEmail("mail@mail.com"))
            .thenReturn(Optional.of(userMock));

            UserDetails result =
                userDetailsService.loadUserByUsername("mail@mail.com");

            // Assert
            assertNotNull(result);

            assertEquals("mail@mail.com", result.getUsername());
            assertEquals("encodedPassword", result.getPassword());


            assertTrue(result.getAuthorities()
                .stream()
                .anyMatch(authority ->
                    authority.getAuthority().equals("ROLE_USER")
                ));


            verify(usersRepository, times(1))
                .getOneUserWithEmail("mail@mail.com");


        }

        @Test
        @DisplayName("Fail")
        void shouldThrowExceptionWhenUserNotFound() {
           when(usersRepository.getOneUserWithEmail("unknown@gmail.com"))
        .thenReturn(Optional.empty());

            UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknown@gmail.com")
            );

            assertEquals("User not found", exception.getMessage());

            verify(usersRepository, times(1))
                .getOneUserWithEmail("unknown@gmail.com");
                }
    
    }
}
