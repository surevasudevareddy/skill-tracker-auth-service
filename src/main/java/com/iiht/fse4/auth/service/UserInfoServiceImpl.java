package com.iiht.fse4.auth.service;

import com.iiht.fse4.auth.entity.UserInfoEntity;
import com.iiht.fse4.auth.model.AddUserResponse;
import com.iiht.fse4.auth.model.LoginUserResponse;
import com.iiht.fse4.auth.model.UserInfo;
import com.iiht.fse4.auth.model.ValidateUserResponse;
import com.iiht.fse4.auth.repository.UserInfoRepository;
import com.iiht.fse4.auth.util.AuthConstants;
import com.iiht.fse4.auth.util.EncryptionUtil;
import com.iiht.fse4.auth.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Override
    public AddUserResponse addUser(UserInfo userInfo) {
        AddUserResponse response = null;
        try {
            UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                    .userName(userInfo.getUserName())
                    .password(encryptionUtil.encrypt(userInfo.getPassword()))
                    .userEmail(userInfo.getEmailId())
                    .userRole(StringUtils.hasLength(userInfo.getUserRole())
                            ? userInfo.getUserRole() : AuthConstants.ROLE_FSE)
                    .createdDate(LocalDateTime.now())
                    .build();

            UserInfoEntity addedUserEntity = userInfoRepository.save(userInfoEntity);
            response = AddUserResponse.builder()
                    .userInfo(UserInfo.builder()
                            .userInfoId(addedUserEntity.getUserInfoId())
                            .userName(addedUserEntity.getUserName())
                            //.password(encryptionUtil.decrypt(addedUserEntity.getPassword()))
                            .emailId(userInfo.getEmailId())
                            .userRole(userInfo.getUserRole())
                            .createdDate(addedUserEntity.getCreatedDate())
                            .updatedDate(addedUserEntity.getUpdatedDate())
                            .build())
                    .status(HttpStatus.OK)
                    .message(AuthConstants.MSG_SUCCESS)
                    .build();
        } catch (Exception ex) {
            response = AddUserResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(AuthConstants.MSG_FAILURE)
                    .build();
        }
        return response;
    }

    @Override
    public LoginUserResponse authenticateUser(final Pair<String, String> userNamePassword) {
        HttpStatus status = HttpStatus.OK;
        String message = AuthConstants.MSG_SUCCESS;
        String jwtToken = "";
        UserInfo userInfo = UserInfo.builder().build();
        try {
            if (isNotEmpty(userNamePassword)) {
                Optional<UserInfoEntity> userEntityOpt = userInfoRepository.getByUserName(userNamePassword.getFirst());
                if (userEntityOpt.isPresent()) {
                    UserInfoEntity userInfoEntity = userEntityOpt.get();
                    if (StringUtils.endsWithIgnoreCase(userNamePassword.getSecond()
                            , encryptionUtil.decrypt(userInfoEntity.getPassword()))) {
                        userInfo.setUserInfoId(userInfoEntity.getUserInfoId());
                        userInfo.setUserName(userNamePassword.getFirst());
                        //userInfo.setPassword(userNamePassword.getSecond());
                        userInfo.setEmailId(userInfoEntity.getUserEmail());
                        userInfo.setUserRole(userInfoEntity.getUserRole());
                        jwtToken = jwtTokenUtil.generateJwtToken(userInfo);
                    } else {
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                        message = "Incorrect Password!";
                    }
                } else {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = "User Not found!";
                }
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "User name and password should not be empty!";
            }
        } catch (Exception ex) {
            log.error("Error occurred while authenticating the user: {} ", userNamePassword.getFirst(), ex);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = AuthConstants.MSG_FAILURE;
        }
        return LoginUserResponse.builder()
                .token(jwtToken)
                .type(AuthConstants.AUTH_TYPE)
                .id(userInfo.getUserInfoId())
                .username(userInfo.getUserName())
                .role(userInfo.getUserRole())
                .email(userInfo.getEmailId())
                .status(status)
                .message(message)
                .build();
    }

    @Override
    public ValidateUserResponse validateUser(String token) {
        HttpStatus status = HttpStatus.OK;
        String message = AuthConstants.MSG_SUCCESS;
        try {
            Jws<Claims> claimsJws = jwtTokenUtil.validateToken(token);
            log.info(claimsJws.toString());

        } catch (Exception ex) {
            log.error("Error occurred while validating the token:", ex);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = ex.getMessage();

        }
        return ValidateUserResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    private boolean isNotEmpty(Pair<String, String> credentials) {
        return (StringUtils.hasLength(credentials.getFirst()) || StringUtils.hasLength(credentials.getSecond()));
    }

}
