# skill-tracker-auth-service
https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-security-algorithms

https://howtodoinjava.com/java/java-security/aes-256-encryption-decryption/

https://www.atatus.com/blog/authentication-and-authorization-for-restful-apis-steps-to-getting-started/

Url:https://www.viralpatel.net/java-create-validate-jwt-token/

Generate Private and Public RSA Key
Generate an RSA private key, of size 2048, and output it to a file named key.pem:
Command:
$ openssl genrsa -out key.pem 2048
$ openssl pkcs8 -topk8 -inform PEM -outform PEM -in key.pem -out privatekey8.pem -nocrypt

$ openssl pkcs8 -topk8 -inform PEM -outform DER -in key.pem -out key.der -nocrypt -- will not work

Extract the public key from the key pair, which can be used in a certificate:
Command:
$ openssl rsa -in key.pem -outform PEM -pubout -out public.pem


https://github.com/netjs/angular-spring-boot-jwt
https://morioh.com/p/fb343fe68657
https://github.com/bezkoder/angular-13-login-registration-example/blob/master/src/app/app-routing.module.ts
https://www.youtube.com/watch?v=QdXHkybzrUU&t=11s
https://www.bezkoder.com/angular-12-spring-boot-jwt-auth/?ref=morioh.com&utm_source=morioh.com#Screenshots
https://github.com/bezkoder/spring-boot-security-jwt-auth-mongodb


user list:

fse1/fse123
fse2/fse123
fse3/fse123
fse4/fse123
fse5/fse123
fse6/fse123
fse7/fse123
fse8/fse123

fse13/
Admin:
admin/admin123


POSTGRES SQL:

fse1/Fse@123'
admin/Admin@123
fse2/Fse2@123