package com.datasec;

import java.util.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenGenerator {
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds
    private static final String SECRET_KEY = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

    public static String generateToken(String username) {
        long nowMillis = System.currentTimeMillis();
        Date expiration = new Date(nowMillis + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return token;
    }
}

