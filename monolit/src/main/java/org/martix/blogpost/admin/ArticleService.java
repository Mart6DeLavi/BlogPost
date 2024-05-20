package org.martix.blogpost.admin;

import java.util.List;

public interface ArticleService {
    List<ArticleEntity> findAllStates();
    void saveState(ArticleEntity state);
    ArticleEntity findStateByTitle(String title);
    ArticleEntity updateState(ArticleEntity state);
    void deleteState(String title);
}
