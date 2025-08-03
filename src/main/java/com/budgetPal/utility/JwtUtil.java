package com.budgetPal.utility;

import com.budgetPal.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(JwtConfig jwtConfig) {
        try {
            String secret = jwtConfig.getSecret();
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            logger.info("JWT Secret Key generated successfully.");
        } catch (Exception e) {
            logger.error("Error generating JWT Secret Key: " + e.getMessage(), e);
            throw new RuntimeException("Failed to generate JWT Secret Key", e);
        }
    }

    public String generateToken(UUID userId, String username, List<String> roles) {
        try {
            return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .claim("userId",userId.toString())
                    .claim("roles", roles)
                    .signWith(secretKey)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating JWT token: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                return (username.equals(claims.getSubject()) && !isTokenExpired(token));
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("Error validating JWT token: " + e.getMessage(), e);
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            logger.error("Error getting username from JWT token: " + e.getMessage(), e);
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return getClaimsFromToken(token).getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Error checking JWT token expiration: " + e.getMessage(), e);
            return true;
        }
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            logger.error("Error parsing JWT token claims: " + e.getMessage(), e);
            return null;
        }
    }

    public List<String> getRolesFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Object rolesObject = claims.get("roles");
            if (rolesObject instanceof List) {
                return (List<String>) rolesObject;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error getting roles from JWT token: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}