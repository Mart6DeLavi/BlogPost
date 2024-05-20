package org.martix.blogpost.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    void deleteByTitle(String title);
    ArticleEntity findStateByTitle(String title);
}
