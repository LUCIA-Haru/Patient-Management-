package com.pm.auth_service.utils.jwt;

import com.pm.auth_service.utils.message.ErrorMessageUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    private final Key secretKey;

    public JWTUtil(@Value("${jwt.secret}") String secret){
        byte[] keyBytes = Base64.getDecoder().decode(
                secret.getBytes(
                        StandardCharsets.UTF_8
                )
        );
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email,String role){
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60 * 10))//10 hrs
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch(SignatureException e){
            throw new JwtException(ErrorMessageUtils.INVALID.formatted("JWT Signature"));
        }catch (ExpiredJwtException e){
            throw new JwtException(ErrorMessageUtils.EXPIRED.formatted("Token"));
        }catch(JwtException e){
            throw new JwtException(ErrorMessageUtils.INVALID.formatted("JWT Token"));
        }
    }
}
