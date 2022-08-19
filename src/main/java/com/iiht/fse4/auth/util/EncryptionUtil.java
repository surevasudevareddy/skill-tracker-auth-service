package com.iiht.fse4.auth.util;

import com.iiht.fse4.auth.config.SecurityConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class EncryptionUtil {

    @Autowired
    private SecurityConfigProperties properties;

    private final byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final IvParameterSpec ivPSpec = new IvParameterSpec(iv);

    /*
            Cipher Info
            Algorithm : for the encryption of electronic data
            mode of operation : to avoid repeated blocks encrypt to the same values.
            padding: ensuring messages are the proper length necessary for certain ciphers
            mode/padding are not used with stream cyphers.
           */
    public String encrypt(String value) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(AuthConstants.FACTORY_ALGO_KEY);
            KeySpec spec = new PBEKeySpec(properties.getSecretKey().toCharArray(), properties.getSalt().getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), AuthConstants.SECRET_KEY_ALGO);

            Cipher cipher = Cipher.getInstance(AuthConstants.CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivPSpec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            log.error("Error during encryption: " + ex.toString());
        }
        return null;
    }

    public String decrypt(String value) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(AuthConstants.FACTORY_ALGO_KEY);
            KeySpec spec = new PBEKeySpec(properties.getSecretKey().toCharArray(), properties.getSalt().getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), AuthConstants.SECRET_KEY_ALGO);

            Cipher cipher = Cipher.getInstance(AuthConstants.CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivPSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
        } catch (Exception ex) {
            log.error("Error during decryption: " + ex);
        }
        return null;
    }


  /*  public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }*/
}
