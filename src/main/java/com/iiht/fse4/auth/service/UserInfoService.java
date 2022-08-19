package com.iiht.fse4.auth.service;

import com.iiht.fse4.auth.model.AddUserResponse;
import com.iiht.fse4.auth.model.UserInfo;
import com.iiht.fse4.auth.model.LoginUserResponse;
import com.iiht.fse4.auth.model.ValidateUserResponse;
import org.springframework.data.util.Pair;

public interface UserInfoService {

    AddUserResponse addUser(UserInfo userInfo);

    LoginUserResponse authenticateUser(final Pair<String, String> userNamePassword);

    ValidateUserResponse validateUser(String token);
}
