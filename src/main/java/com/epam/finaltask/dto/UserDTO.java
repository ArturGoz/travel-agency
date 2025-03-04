package com.epam.finaltask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String role;
    private String phoneNumber;
    private Double balance;
    private boolean accountStatus;
}
