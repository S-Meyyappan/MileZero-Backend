package com.springboot.milezero.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility {

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    private final static long expiryTime = (long) 24 * 60 * 60 * 1000;

    // Helper method to safely generate a cryptographic SecretKey from your configured string
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Extracts Username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract date of expiry also from token --> Spring checks this
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Spring uses this method to check if the incoming token is the same that was given using claim
    // Claims are nothing but the request payload ie re the key-value data like username, expiration date
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // T/ This method acts as the "security guard" that decrypts, verifies, and reads the incoming token.
    private Claims extractAllClaims(String token) {
        /*JWT validation splits the token, reads the Base64 data,
        mixes it with your secret key to recalculate the signature, and
        confirms it matches the incoming signature to prove the data hasn't been altered.
        if altered throws SignatureException
         */
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // This is to check if the token has expired or is still valid
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // This is the method we call from jwtFilter to generate token
    public String generateToken(String username) { //We use this
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // This method creates a token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject) // This is the username of logged in user
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSigningKey())
                .compact();
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
