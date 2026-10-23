package com.myspring.demo.account.dto;

public record AccountResponse(
        Long id,
        String firstname,
        String lastname,
        String email
) {}

// public enum AccountStatus {
//     ACTIVE,
//     INACTIVE,
//     BLOCKED,
//     CLOSED
// }

// import java.math.BigDecimal;
// import java.time.Instant;

// public record AccountResponse(
//         Long id,
//         String accountNumber,
//         BigDecimal balance,
//         String currency,
//         AccountStatus status,
//         Instant createdAt
// ) {
// }

// OR 

// map to the DTO response 
// Keep entity-to-response conversion outside the controller.
// import org.springframework.stereotype.Component;

// @Component
// public class AccountMapper {

//     public AccountResponse toResponse(Account account) {
//         return new AccountResponse(
//                 account.getId(),
//                 account.getAccountNumber(),
//                 account.getBalance(),
//                 account.getCurrency(),
//                 account.getStatus(),
//                 account.getCreatedAt()
//         );
//     }
// }


