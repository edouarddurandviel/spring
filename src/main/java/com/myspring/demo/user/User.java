package com.myspring.demo.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

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

    protected User(){}

    public User(String email, String password, String firstname, String lastname){
        this.email  = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getUserId(){
        return id;
    }

    public String getUserEmail(){
        return email;
    }

    public String getUserPassword(){
        return email;
    }

    public String getUserFirstName(){
        return firstname;
    }

    public String getUserLastName(){
        return lastname;
    } 

    public void setUserId(Long id){
        this.id = id;
    }

    public void setUserEmail(String email){
        this.email = email;
    }

    public void setUserPassword(String password){
        this.password = password;;
    }

    public void setUserFirstName(String firstname){
        this.firstname = firstname;
    }

    public void setUserLastName(String lastname){
        this.lastname = lastname;
    } 
}