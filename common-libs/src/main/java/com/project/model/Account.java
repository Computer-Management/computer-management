package com.project.model;

import com.project.payload.StandardEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "Account.findByUsername", query = "select a from Account a where a.username = :username")
})
public class Account extends StandardEntity {
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "identityNumber", unique = true)
    private String identityNumber;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @Column(name = "isAdmin")
    private boolean isAdmin;

    public static Account findByUsername(String username) {
        return find("#Account.findByUsername", Parameters.with("username", username)).firstResult();
    }
}


