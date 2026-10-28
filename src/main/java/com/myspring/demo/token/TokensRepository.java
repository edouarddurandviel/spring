package com.myspring.demo.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokensRepository extends JpaRepository<Tokens, Long> {
    @Query("SELECT t FROM Tokens t")
    List<Tokens> getAllTokens();

    @Query("SELECT t FROM Tokens t WHERE t.id = :id")
    Optional<Tokens> getOneToken(@Param("id") Long id);

    @Query("SELECT t FROM Tokens t WHERE t.User_Id = :User_Id")
    Optional<Tokens> getOneTokenWithEmail(@Param("User_Id") int User_Id);

    @Query("SELECT EXISTS (SELECT 1 FROM Tokens WHERE User_Id = :User_Id)")
    Boolean alreadyCreated(@Param("User_Id") int User_Id);
}