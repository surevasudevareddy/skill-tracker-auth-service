package com.iiht.fse4.auth.util;

import com.google.gson.Gson;

import java.util.concurrent.ThreadLocalRandom;

public class AuthUtil {

    public static String convertToJson(Object object) {
        return new Gson().toJson(object);
    }


    public static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 999999);
    }

}
