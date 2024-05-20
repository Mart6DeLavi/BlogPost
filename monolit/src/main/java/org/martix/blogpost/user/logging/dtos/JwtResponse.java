package org.martix.blogpost.user.logging.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The JwtResponse class is a data class that encapsulates the token received after successful JWT authentication.
 */

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}