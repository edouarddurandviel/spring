package com.myspring.demo.account;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myspring.demo.account.dto.AccountValidation;
import com.myspring.demo.account.dto.AccountResponse;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public AccountResponse getOneAccount(Long id) {
        Account account = repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Account not found with id: " + id
        ));
        
        return new AccountResponse(
            account.getAccountId(),
            account.getAccountFirstName(),
            account.getAccountLastName(),
            account.getAccountEmail()
        );
    }

    public List<AccountResponse> getAllAccounts() {

        List<AccountResponse> accounts = repository.findAll()
        .stream()
        .map((account) -> new AccountResponse( 
                    account.getAccountId(),
                    account.getAccountFirstName(),
                    account.getAccountLastName(),
                    account.getAccountEmail()
        )).toList();
        return accounts;
    }

    public AccountResponse createAccount(AccountValidation request) {

        if(repository.existsByEmail(request.email())){
            throw new RuntimeException("Account Allready exists");
        }
         
        Account account = new Account();
        account.setAccountEmail(request.email());
        account.setAccountFirstName(request.firstname());
        account.setAccountLastName(request.lastname());
        account.setAccountPassword(passwordEncoder.encode(request.password()));

        Account createdAccount = repository.save(account);

        return new AccountResponse(
            createdAccount.getAccountId(),
            createdAccount.getAccountFirstName(),
            createdAccount.getAccountLastName(),
            createdAccount.getAccountEmail()
        );
    }


    public AccountResponse login(AccountValidation request) {

        Account account = repository.getOneAccountWithEmail(request.email()).orElseThrow(
            () -> new RuntimeException("Account Account does not exist")
        );

        if(!passwordEncoder.matches(request.password(), account.getAccountPassword())){
            throw new RuntimeException("Wrong password");
        }

        return new AccountResponse(
            account.getAccountId(),
            account.getAccountFirstName(),
            account.getAccountLastName(),
            account.getAccountEmail()
        );
    }


    public AccountResponse addOneAccount(AccountValidation request) {
        
        Account account = new Account();
        account.setAccountFirstName(request.firstname());
        account.setAccountPassword(request.password());
        account.setAccountLastName(request.lastname());
        account.setAccountEmail(request.email());

        Account createdAccount = repository.save(account);

        return new AccountResponse(
            createdAccount.getAccountId(),
            createdAccount.getAccountFirstName(),
            createdAccount.getAccountLastName(),
            createdAccount.getAccountEmail()
        );
    }

    public AccountResponse updateOneAccount(Long id, AccountValidation request) {
        Account account = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Account not found")
        );

        account.setAccountFirstName(request.firstname());
        account.setAccountPassword(request.password());
        account.setAccountLastName(request.lastname());
        account.setAccountEmail(request.email());

        Account updatedAccount = repository.save(account);

        return new AccountResponse(
            updatedAccount.getAccountId(),
            updatedAccount.getAccountFirstName(),
            updatedAccount.getAccountLastName(),
            updatedAccount.getAccountEmail()
        );
    }

    public AccountResponse deleteOneAccount(Long id) {
        Account account = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Account not found")
        );

        repository.delete(account);

        return new AccountResponse(
            account.getAccountId(),
            account.getAccountFirstName(),
            account.getAccountLastName(),
            account.getAccountEmail()
        );
    }

    public void deleteOneAccountNoContent(Long id) {
        Account account = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Account not found")
        );
        repository.delete(account);
    }

}