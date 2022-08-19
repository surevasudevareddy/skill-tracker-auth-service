package com.iiht.fse4.auth.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserInfo {

    private int userInfoId;
    private String userName;
    private String password;
    private String emailId;
    private String userRole;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
