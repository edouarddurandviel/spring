package com.myspring.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> getOneUser(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getOneUserWithEmail(@Param("email") String email);

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE email = :email)")
    Boolean alreadyCreated(@Param("email") String email);
}