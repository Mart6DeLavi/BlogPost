package org.martix.blogpost.user.logging.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
