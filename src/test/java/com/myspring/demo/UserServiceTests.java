package com.myspring.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring.demo.rights.Rights;
import com.myspring.demo.rights.dto.RightsResponse;
// import service
import com.myspring.demo.user.UserService;
import com.myspring.demo.user.Users;
import com.myspring.demo.user.UsersRepository;
import com.myspring.demo.user.dto.UserResponse;
import com.myspring.demo.user.dto.UserWithRightsResponse;

@ExtendWith(MockitoExtension.class)

@DisplayName("UserService class") // describe
public class UserServiceTests {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

    private static Users userMock;

    private static Rights rightsMock;


    @BeforeAll// beforeach
    static void setUp() {

        userMock = mock(Users.class);

        when(userMock.getUserEmail())
            .thenReturn("john@gmail.com");


        when(userMock.getUserId())
            .thenReturn(1L);

        rightsMock = mock(Rights.class);

        when(rightsMock.getRightId())
            .thenReturn(1L);

             when(rightsMock.getRightId())
            .thenReturn(1L);

        when(rightsMock.getRightsUser())
            .thenReturn(userMock);


        when(userMock.getRights())
            .thenReturn(rightsMock);
    }



    @Nested
    @DisplayName("getOneUser()")
    class getOneUserTest {

        @Test
        @DisplayName("Success")
        void shouldgetOneUser() {

            when(usersRepository.findWithRightsById(userMock.getUserId()))
            .thenReturn(Optional.of(userMock));

            UserWithRightsResponse result =
                userService.getOneUser(userMock.getUserId());

            // Assert
            assertNotNull(result);
            assertEquals(userMock.getUserEmail(), result.userEmail());
            assertEquals(userMock.getUserId(), result.userId());


            assertNotNull(rightsMock);
            assertEquals(rightsMock.getClass(), Rights.class);
            assertEquals(rightsMock.getRightsUser(), userMock);

        
            // should make test of records
            assertEquals(userMock.getUserId(), result.userId());
            verify(usersRepository).findWithRightsById(userMock.getUserId());

            // objectMapper
            verify(objectMapper).convertValue(
                rightsMock.getRightsContent(),
                RightsResponse.class
            );
        }

        @Test
        @DisplayName("Fail")
        void shouldThrowExceptionWhenUserNotFound() {
           when(usersRepository.findWithRightsById(200L))
                .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userService.getOneUser(200L)
        );

        assertEquals("your User not found", exception.getMessage());
        }
    }




    @Nested
    @DisplayName("getOneUserWithEmail()")
    class getOneUserWithEmailTest {


        @Test
        @DisplayName("Success")
        void shouldgetOneUser() {

            when(usersRepository.getOneUserWithEmail(userMock.getUserEmail()))
                .thenReturn(Optional.of(userMock));

            UserResponse result =
                userService.getOneUserWithEmail(userMock.getUserEmail());

            assertNotNull(result);
            assertEquals(userMock.getUserEmail(), result.email());
            assertEquals(userMock.getUserId(), result.id());

        }


        @Test
        @DisplayName("Fail")
        void shouldRuntimeException() {
            when(usersRepository.getOneUserWithEmail("test-error@mail.com"))
                .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.getOneUserWithEmail("test-error@mail.com")
            );

            assertEquals("Email could not held finding the user.", exception.getMessage());
        }


    }



}
