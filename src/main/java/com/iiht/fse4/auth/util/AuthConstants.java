package com.iiht.fse4.auth.util;

public interface AuthConstants {

    String AUTH_API = "/auth";

    String MSG_SUCCESS = "SUCCESS";
    String MSG_FAILURE = "FAILURE";

    String FACTORY_ALGO_KEY = "PBKDF2WithHmacSHA256";
    String SECRET_KEY_ALGO = "AES";
    String CIPHER = "AES/CBC/PKCS5Padding";

    String AUTHORIZATION = "authorization";

    String AUTH_TYPE = "Bearer";

    String ROLE_FSE = "FSE";
}
