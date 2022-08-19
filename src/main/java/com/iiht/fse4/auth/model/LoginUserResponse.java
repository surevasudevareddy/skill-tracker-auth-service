package com.iiht.fse4.auth.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class LoginUserResponse {
    private String token;
    private String type;
    private int id;
    private String username;
    private String email;
    private String role;
    private HttpStatus status;
    private String message;
}
