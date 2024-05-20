package org.martix.blogpost.user.logging.dtos;

import lombok.Data;

/**
 * The JwtRequest class is a data class that encapsulates the username and password for JWT authentication.
 */

@Data
public class JwtRequest {
    private String username;
    private String password;
}
