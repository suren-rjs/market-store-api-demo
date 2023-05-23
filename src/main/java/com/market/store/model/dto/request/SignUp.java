package com.market.store.model.dto.request;

import com.market.store.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String email;
    private String phone;
    private String username;
    private String password;
    private UserType userType;
}
