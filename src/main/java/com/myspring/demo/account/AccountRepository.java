package com.myspring.demo.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT * FROM account a")
    List<Account> getAllAccounts();

    @Query("SELECT * FROM account a WHERE a.id = :id")
    Optional<Account> getOneAccount(@Param("id") Long id);

    @Query("SELECT * FROM account a WHERE a.email = :email")
    Optional<Account> getOneAccountWithEmail(@Param("email") String email);

    @Query("SELECT EXISTS (SELECT 1 FROM account WHERE email = :email)")
    Boolean alreadyCreated(@Param("email") String email);
}