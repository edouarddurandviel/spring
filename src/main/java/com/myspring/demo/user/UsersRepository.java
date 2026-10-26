package com.myspring.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u")
    List<Users> getAllUsers();

    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Optional<Users> getOneUser(@Param("id") Long id);

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> getOneUserWithEmail(@Param("email") String email);

    @Query("SELECT EXISTS (SELECT 1 FROM Users WHERE email = :email)")
    Boolean alreadyCreated(@Param("email") String email);
}