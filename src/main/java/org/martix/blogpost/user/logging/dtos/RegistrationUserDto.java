package org.martix.blogpost.user.logging.dtos;

import lombok.Data;

/**
 * The RegistrationUserDto class is a data class that encapsulates the user details required for registration.
 */

@Data
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
