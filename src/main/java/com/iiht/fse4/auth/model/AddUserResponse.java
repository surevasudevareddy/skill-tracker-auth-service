package com.iiht.fse4.auth.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class AddUserResponse {
    private UserInfo userInfo;
    private HttpStatus status;
    private String message;
}
