package org.martix.articleservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

    @Autowired
    private static RestTemplate restTemplate;

    private static final String ARTICLE_SERVICE_URL = "http://localhost:8080/articles";

    @GetMapping("/findAllArticles")
    public List<ArticleEntity> findAllArticles() {
        return restTemplate.getForObject(ARTICLE_SERVICE_URL + "/findAllArticles", List.class, ArticleEntity.class);
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@RequestBody ArticleEntity article) {
        restTemplate.postForObject(ARTICLE_SERVICE_URL + "/saveArticle", article, ArticleEntity.class);
        return "Saved";
    }

    @GetMapping("/{title}/find_article")
    public ArticleEntity findArticleByTitle(@PathVariable String title) {
        return restTemplate.getForObject(ARTICLE_SERVICE_URL + "/findArticleByTitle/" + title, ArticleEntity.class);
    }

    @PutMapping("/update_article")
    public ResponseEntity<ArticleEntity> updateArticle(@RequestBody ArticleEntity article) {
        article.setUpdatedAt(LocalDate.now());

        HttpEntity<ArticleEntity> requestEntity = new HttpEntity<>(article);
        ResponseEntity<ArticleEntity> responseEntity = restTemplate.exchange(
                ARTICLE_SERVICE_URL,
                HttpMethod.PUT,
                requestEntity,
                ArticleEntity.class
        );

        return responseEntity;
    }

    @DeleteMapping("/{title}/delete_article")
    public void deleteArticle(@PathVariable String title) {
        restTemplate.delete(ARTICLE_SERVICE_URL + "/deleteArticle/" + title);
    }
}
