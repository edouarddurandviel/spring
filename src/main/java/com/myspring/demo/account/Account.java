package com.myspring.demo.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "firstname", nullable = true)
    private String firstname;

    @Column(name = "lastname", nullable = true)
    private String lastname;

    protected Account(){}

    public Account(String email, String password, String firstname, String lastname){
        this.email  = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getAccountId(){
        return id;
    }

    public String getAccountEmail(){
        return email;
    }

    public String getAccountPassword(){
        return email;
    }

    public String getAccountFirstName(){
        return firstname;
    }

    public String getAccountLastName(){
        return lastname;
    } 

    public void setAccountId(Long id){
        this.id = id;
    }

    public void setAccountEmail(String email){
        this.email = email;
    }

    public void setAccountPassword(String password){
        this.password = password;;
    }

    public void setAccountFirstName(String firstname){
        this.firstname = firstname;
    }

    public void setAccountLastName(String lastname){
        this.lastname = lastname;
    } 
}