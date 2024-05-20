package org.martix.blogpost.user.logging.services;

import lombok.AllArgsConstructor;
import org.martix.blogpost.user.logging.entities.Role;
import org.martix.blogpost.user.logging.repositories.RoleRepository;
import org.springframework.stereotype.Service;

/**
 * The RoleService class provides role-related services.
 */

@Service
@AllArgsConstructor
public class RoleService {
    /**
     * A repository for role-related operations.
     */
    private final RoleRepository roleRepository;

    /**
     * Retrieves the user role from the role repository.
     *
     * @return the Role object corresponding to "ROLE_USER"
     */
    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
