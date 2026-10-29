
package com.myspring.demo.rights;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;


import com.myspring.demo.user.Users;

@Entity
@Table(name = "\"Rights\"")
public class Rights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb", nullable = false, unique = true)
    private JsonNode content;

    // ok
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"User_Id\"", nullable = false)
    private Users user;
    // ok

    @Column(name = "\"createdAt\"", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "\"updatedAt\"", nullable = false)
    private LocalDateTime updatedAt;

    protected Rights() {}

    public Rights(JsonNode content, Users user) {
        this.content = content;
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getRightId() {
        return id;
    }
    

    public JsonNode getRightsContent() {
        return content;
    }

    public Users getRightsUser() {
        return user;
    }


    public LocalDateTime getRightsCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getRightsUpdatedAt() {
        return updatedAt;
    }

    public void setRightsId(Long id) {
        this.id = id;
    }

    public void setRightsContent(JsonNode content) {
        this.content = content;
    }

    public void setRightsUser_Id(Users user) {
        this.user = user;
    }
}
