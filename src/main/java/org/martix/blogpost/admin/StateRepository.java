package org.martix.blogpost.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Long> {
    void deleteByTitle(String title);
    StateEntity findStateByTitle(String title);
}
