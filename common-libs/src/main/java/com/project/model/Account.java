package com.project.model;

import com.project.payload.StandardEntity;
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
        @NamedQuery(name = "findAllAccount", query = "SELECT a FROM Account a"),
        @NamedQuery(name = "findByUsername", query = "select a from Account a where a.username = :username"),
        @NamedQuery(name = "findByEmail", query = "select a from Account a where a.email = :email"),
        @NamedQuery(name = "findByPhoneNumber", query = "select a from Account a where a.phoneNumber = :phoneNumber"),
        @NamedQuery(name = "findByIdentityNumber", query = "select a from Account a where a.identityNumber = :identityNumber"),
})
public class Account extends StandardEntity {
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "identityNumber")
    private String identityNumber;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "isAdmin")
    private boolean isAdmin;
}


