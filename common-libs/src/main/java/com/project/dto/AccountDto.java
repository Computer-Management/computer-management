package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.project.model.Account}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
    Long id;
    String uuid = UUID.randomUUID().toString();
    LocalDateTime createAt;
    LocalDateTime updateAt;
    String fullName;
    String username;
    String password;
    String identityNumber;
    String dateOfBirth;
    String email;
    boolean gender;
    String phoneNumber;
    boolean isAdmin;
}
