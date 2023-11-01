package com.datasec;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

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

    public static Jws<Claims> theUserBasedOnToken(String token) {
    String secret = SECRET_KEY;
    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
                                    SignatureAlgorithm.HS256.getJcaName());
    try {
        Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(hmacKey)
        .build()
        .parseClaimsJws(token);

        return jwt;
        } catch (SignatureException e) {
            System.out.println("Token is not valid");
            return null; // Token is not valid
        }
    }

    public static boolean isTokenValid(String token) {
        String secret = SECRET_KEY;
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        try {
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            return true; // Token is valid
        } catch (SignatureException e) {
            return false; // Token is not valid
        }
    }
}
