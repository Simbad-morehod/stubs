package com.ru.controller.getToken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@RestController
public class Controller {
int random5nums;
int random4nums;
    String jwtString = "";
    @GetMapping("/access-token")
    public ResponseEntity<String> generateAccessToken() throws GeneralSecurityException {
        Instant now = Instant.now();
        final SignatureAlgorithm ALG = SignatureAlgorithm.ES256;
        Date expiryDate = Date.from(now.plusMillis(90000));
        System.out.println(now + "  " +expiryDate);
        random4nums= (int) (1000+Math.random() * 9999);
        while(random4nums >= 10000){random4nums= (int) (1000+Math.random() * 9999);}
        random5nums= (int) (10000+Math.random() * 99999);
        while(random4nums >= 100000){random4nums= (int) (10000+Math.random() * 99999);}
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withSubject("tm@test.ru")
                .withClaim("exampleArray", Arrays.asList(new String[]{"profile"}))
                .withClaim("example", "internal")
                .withIssuer("service")
                .withClaim("ctxi", UUID.randomUUID().toString())
                .withClaim("iat", Date.from(now))
                .withClaim("exp",expiryDate)
                .withJWTId(UUID.randomUUID().toString());
        jwtString = jwtBuilder.sign(AlgorithmBuilder.buildECDSA256("publickey", "privatekey"));
        System.out.println(jwtString);
        return ResponseEntity.ok(jwtString);
    }
}
