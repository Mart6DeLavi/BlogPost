package org.martix.articleservice;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public List<ArticleEntity> findAllStates() {
        return articleRepository.findAll();
    }

    @Override
    public void saveState(ArticleEntity article) {
        articleRepository.save(article);
    }

    @Override
    public ArticleEntity findArticleByTitle(String title) {
        return articleRepository.findArticleEntityByTitle(title);
    }

    @Override
    public ArticleEntity updateArticle(ArticleEntity article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(String title) {
        articleRepository.deleteByTitle(title);
    }
}
