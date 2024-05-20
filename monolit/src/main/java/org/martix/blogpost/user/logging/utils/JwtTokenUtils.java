package org.martix.blogpost.user.logging.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The JwtTokenUtils class provides utility methods for JWT token operations.
 */

@Component
public class JwtTokenUtils {

    /**
     * The secret key used for signing the JWT.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * The lifetime of the JWT.
     */
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    /**
     * Generates a JWT token for a user.
     *
     * @param userDetails the user's details
     * @return a JWT token
     */
    public String generationToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Retrieves all claims from a JWT token.
     *
     * @param token the JWT token
     * @return a Claims object containing all claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Retrieves the roles from a JWT token.
     *
     * @param token the JWT token
     * @return a list of roles
     */
    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
}
