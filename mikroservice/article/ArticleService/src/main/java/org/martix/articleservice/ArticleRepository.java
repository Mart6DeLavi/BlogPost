package org.martix.articleservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    void deleteByTitle(String title);
    ArticleEntity findArticleEntityByTitle(String title);
}
