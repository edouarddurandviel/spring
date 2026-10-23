package com.myspring.demo.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a")
    List<Account> getAllAccounts();

    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> getOneAccount(@Param("id") Long id);

    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Optional<Account> getOneAccountWithEmail(@Param("email") String email);

    @Query(
        value = "SELECT EXISTS (SELECT 1 FROM Account WHERE email = :email)",
        nativeQuery = true
    )
    boolean existsByEmail(@Param("email") String email);
}