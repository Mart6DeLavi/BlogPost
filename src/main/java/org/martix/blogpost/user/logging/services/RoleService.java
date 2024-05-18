package org.martix.blogpost.user.logging.services;

import lombok.AllArgsConstructor;
import org.martix.blogpost.user.logging.entities.Role;
import org.martix.blogpost.user.logging.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
