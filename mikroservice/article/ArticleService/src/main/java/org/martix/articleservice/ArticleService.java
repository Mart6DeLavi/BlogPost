package org.martix.articleservice;

import java.util.List;

public interface ArticleService {
    List<ArticleEntity> findAllStates();
    void saveState(ArticleEntity article);
    ArticleEntity findArticleByTitle(String title);
    ArticleEntity updateArticle(ArticleEntity article);
    void deleteArticle(String title);
}
