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


    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private ObjectMapper objectMapperMock;


    @Nested
    @DisplayName("getOneUser()")
    class getOneUserTest {

        @Test
        @DisplayName("Success")
        void shouldgetOneUser() {

            // Arrange / define
            Users userMock = mock(Users.class);
            Rights rightsMock = mock(Rights.class);
            RightsResponse rightsResponseMock =
                mock(RightsResponse.class);

            when(userMock.getUserId()).thenReturn(1L);
            when(userMock.getUserEmail())
                .thenReturn("mail@mail.com");
            when(userMock.getRights()).thenReturn(rightsMock);

            when(rightsMock.getRightsContent())
                .thenReturn(
                    com.fasterxml.jackson.databind.node.JsonNodeFactory.instance
                        .objectNode()
                        .put("read", true)
                        .put("write", true)
                );


            when(usersRepositoryMock.findWithRightsById(1L))
                .thenReturn(Optional.of(userMock));

            when(objectMapperMock.convertValue(
                rightsMock.getRightsContent(),
                RightsResponse.class
            )).thenReturn(rightsResponseMock);

            // Act
            UserWithRightsResponse result =
                userService.getOneUser(1L);

            // Assert
            assertNotNull(result);
            assertEquals("mail@mail.com", result.userEmail());
            assertEquals(1L, result.userId());
    
            verify(usersRepositoryMock, times(1)).findWithRightsById(1L);

            verify(objectMapperMock, times(1)).convertValue(
                rightsMock.getRightsContent(),
                RightsResponse.class
            );
        }

        @Test
        @DisplayName("Fail")
        void shouldThrowExceptionWhenUserNotFound() {
           when(usersRepositoryMock.findWithRightsById(200L))
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
            Users userMock = mock(Users.class);

            // define values
            when(userMock.getUserEmail())
                .thenReturn("mail@mail.com");

            when(userMock.getUserId())
                .thenReturn(1L);

            when(usersRepositoryMock.getOneUserWithEmail(
                "mail@mail.com"
            )).thenReturn(Optional.of(userMock));

            // use Action
            UserResponse result =
                userService.getOneUserWithEmail("mail@mail.com");

            // Assert
            assertNotNull(result);
            assertEquals("mail@mail.com", result.email());
            assertEquals(1L, result.id());

            verify(usersRepositoryMock)
                .getOneUserWithEmail("mail@mail.com");

        }


        @Test
        @DisplayName("Fail")
        void shouldRuntimeException() {
            when(usersRepositoryMock.getOneUserWithEmail("test-error@mail.com"))
                .thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.getOneUserWithEmail("test-error@mail.com")
            );

            assertEquals("Email could not held finding the user.", exception.getMessage());
        }


    }



}
