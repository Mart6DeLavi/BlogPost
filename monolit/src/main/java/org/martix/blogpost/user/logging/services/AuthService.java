package org.martix.blogpost.user.logging.services;

import lombok.AllArgsConstructor;
import org.martix.blogpost.user.logging.dtos.JwtRequest;
import org.martix.blogpost.user.logging.dtos.JwtResponse;
import org.martix.blogpost.user.logging.dtos.RegistrationUserDto;
import org.martix.blogpost.user.logging.dtos.UserDto;
import org.martix.blogpost.user.logging.entities.User;
import org.martix.blogpost.user.logging.exceptions.AppError;
import org.martix.blogpost.user.logging.utils.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The AuthService class provides authentication and user registration services.
 */

@Service
@AllArgsConstructor
public class AuthService {
    /**
     * A service for user-related operations.
     */
    private final UserService userService;

    /**
     * A utility class for JWT token operations.
     */
    private final JwtTokenUtils jwtTokenUtils;

    /**
     * A manager for authentication operations.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user and creates an authentication token.
     *
     * @param authRequest the authentication request containing the username and password
     * @return a ResponseEntity containing either an AppError or a JwtResponse with the token
     */
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Uncorrected login or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generationToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Creates a new user.
     *
     * @param registrationUserDto the data transfer object containing the new user's details
     * @return a ResponseEntity containing either an AppError or a UserDto with the new user's details
     */
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }

        if(userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с данным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
