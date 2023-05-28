package com.api.arv.model.dto.request.auth;

import com.api.arv.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String phone;
    private String username;
    private String uid;
    private UserType userType;
    private String email;
}
