package com.epam.finaltask.dto;

import com.epam.finaltask.enums.Role;
import com.epam.finaltask.model.Voucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String role;
/*    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Voucher> vouchers;*/
    private String phoneNumber;
    private Double balance;
    private boolean accountStatus;
}
