package org.martix.blogpost.user.logging.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The UserDto class is a data class that encapsulates the user details.
 */

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
