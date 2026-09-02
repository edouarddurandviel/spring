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

    @Column(name = "fullname", nullable = true)
    private String fullname;

    @Column(name = "position", nullable = true)
    private String position;

    protected User(){}

    public User(String email, String password, String fullname, String position){
        this.email  = email;
        this.password = password;
        this.fullname = fullname;
        this.position = position;
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

    public String getUserFullName(){
        return fullname;
    }

    public String getUserPosition(){
        return position;
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

    public void setUserFullName(String fullname){
        this.fullname = fullname;
    }

    public void setUserPosition(String position){
        this.position = position;
    } 
}