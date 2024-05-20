package org.martix.blogpost.user.logging.services;

import org.martix.blogpost.user.logging.dtos.RegistrationUserDto;
import org.martix.blogpost.user.logging.entities.User;
import org.martix.blogpost.user.logging.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The UserService class provides user-related services and implements the UserDetailsService interface.
 */

@Service
public class UserService implements UserDetailsService {

    /**
     * A repository for user-related operations.
     */
    private UserRepository userRepository;

    /**
     * A service for role-related operations.
     */
    private RoleService roleService;

    /**
     * An encoder for password-related operations.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Sets the UserRepository.
     *
     * @param userRepository the UserRepository to set
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sets the RoleService.
     *
     * @param roleService the RoleService to set
     */
    @Autowired
    public void setRoleRepository(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Sets the PasswordEncoder.
     *
     * @param passwordEncoder the PasswordEncoder to set
     */
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the User if found, or an empty Optional otherwise
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to load
     * @return a UserDetails object containing the user's details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User %s not found", username)
                ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Creates a new user.
     *
     * @param registrationUserDto the data transfer object containing the new user's details
     * @return the newly created User
     */
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }
}
