package org.martix.blogpost.user.logging.contorllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The MainController class provides endpoints for different types of data.
 */


@RestController
@RequiredArgsConstructor
public class MainController {

    /**
     * Endpoint to retrieve unsecured data.
     * @return A string containing "Unsecured Data".
     */

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured Data";
    }

    /**
     * Endpoint to retrieve secured data.
     * @return A string containing "Secured Data".
     */

    @GetMapping("/secured")
    public String securedData() {
        return "Secured Data";
    }

    /**
     * Endpoint to retrieve admin data.
     * @return A string containing "Admin data".
     */
    @GetMapping("/admin")
    public String adminData() {
        return "Admin data";
    }

    /**
     * Endpoint to retrieve user data.
     * @param principal The Principal object containing the user's details.
     * @return The name of the user.
     */
    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}
