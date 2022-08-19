package com.iiht.fse4.auth.config;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Data
@Component
@FieldDefaults(level = PRIVATE)
public class SecurityConfigProperties {

    @Value("${security.crypto.secretkey}")
    String secretKey;
    @Value("${security.crypto.salt}")
    String salt;
    @Value("${security.jwt.signing.private-key}")
    String jwtSigningPrivateKey;
    @Value("${security.jwt.signing.public-key}")
    String jwtSigningPublicKey;

}
