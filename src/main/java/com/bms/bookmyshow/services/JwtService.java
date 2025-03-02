package com.bms.bookmyshow.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private static final String SECRET = "F89CC069E8FE6BEE979B5044652C92639C5146AB8D176FC16E6870D19574965DF1357A7E9BAA13C76E7C67780E0C0E1C7BE0FDF06E7426AAFA66576CA3E776DC";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey=Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    
    public String extractUsername(String jwt){
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
            return claims;
        }catch (MalformedJwtException e){
            throw  new RuntimeException("Invalid jwt format ",e);
        }catch (SignatureException e){
            throw  new RuntimeException("Invalid jwt signature ",e);
        }catch (ExpiredJwtException e){
            throw  new RuntimeException("Jwt has expired ",e);
        }catch (Exception e){
            throw new RuntimeException("Error parsing JWT",e);
        }
    }

    public boolean isTokenValid(String jwt){
        Claims claims=getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }
}
