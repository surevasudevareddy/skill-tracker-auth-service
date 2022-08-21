package com.iiht.fse4.auth.controller;

import com.iiht.fse4.auth.model.AddUserResponse;
import com.iiht.fse4.auth.model.LoginUserResponse;
import com.iiht.fse4.auth.model.UserInfo;
import com.iiht.fse4.auth.model.ValidateUserResponse;
import com.iiht.fse4.auth.service.UserInfoService;
import com.iiht.fse4.auth.util.AuthConstants;
import com.iiht.fse4.auth.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(AuthConstants.AUTH_API)
public class AuthController {


    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/hi")
    public String hi() {
        return "Hello There!!";
    }

    @PostMapping("/addUser")
    public ResponseEntity<AddUserResponse> addUserInfo(@RequestBody UserInfo userInfo) {
        AddUserResponse response = userInfoService.addUser(userInfo);
        return new ResponseEntity<>(response, response.getStatus());
    }


    @PostMapping("/login1")
    public ResponseEntity<LoginUserResponse> authenticateUserJwt(@RequestHeader HttpHeaders headers) {
        final Pair<String, String> userNamePassword = SecurityUtil.decodeBase64(headers);
        LoginUserResponse response = userInfoService.authenticateUser(userNamePassword);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> authenticateUser(@RequestHeader HttpHeaders headers) {
        final Pair<String, String> userNamePassword = SecurityUtil.decodeBase64(headers);
        LoginUserResponse response = userInfoService.authenticateUser(userNamePassword);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/validateUser")
    public ResponseEntity<ValidateUserResponse> validateUser(@RequestHeader HttpHeaders headers) {
        String token = SecurityUtil.extractToken(headers);
        ValidateUserResponse response = userInfoService.validateUser(token);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
