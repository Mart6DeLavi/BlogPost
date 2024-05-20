package org.martix.blogpost.user.logging.contorllers;

import lombok.RequiredArgsConstructor;
import org.martix.blogpost.user.logging.dtos.JwtRequest;
import org.martix.blogpost.user.logging.dtos.RegistrationUserDto;
import org.martix.blogpost.user.logging.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The AuthController class provides endpoints for authentication and registration.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/logging")
public class AuthController {
    private final AuthService authService;

    /**
     * Endpoint to create an authentication token.
     * @param authRequest The JwtRequest containing the authentication details.
     * @return A ResponseEntity containing the authentication token or an error message.
     */
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }


    /**
     * Endpoint to register a new user.
     * @param registrationUserDto The RegistrationUserDto containing the new user's details.
     * @return A ResponseEntity containing the result of the registration process.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }
}
