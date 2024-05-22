package org.martix.commentservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private static RestTemplate restTemplate;

    private static final String COMMENT_SERVICE_URL = "http:/localhost:8080/comments";
    private static final String ARTICLE_SERVICE_URL = "http:/localhost:8080/articles";
    private static final String TITLE = "/{title}";
    private static final String WRITE_AND_SAVE_COMMENT = "/{title}/write_comment";
    private static final String FIND_COMMENT_BY_TEXT = "/{title}/{text}find_comment";

    @GetMapping(TITLE)
    public List<CommentEntity> getAllCommentsFromArticleByTitle(@PathVariable("title") String title) {
        ResponseEntity<ArticleDTO> response = restTemplate.getForEntity(
                ARTICLE_SERVICE_URL + "/blogpost" + title, ArticleDTO.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            return commentService.getAllCommentsByTitle(title);
        }
        else {
            return Collections.emptyList();
        }
    }

    @PostMapping(WRITE_AND_SAVE_COMMENT)
    public ResponseEntity<?> saveComment(@PathVariable String title,
                                         @RequestBody CommentEntity comment,
                                         Principal principal) {
        ResponseEntity<ArticleDTO> response = restTemplate.postForEntity(
                ARTICLE_SERVICE_URL + "/blogpost" + title, comment, ArticleDTO.class);
        if(response.getStatusCode() == HttpStatus.OK) {
            comment.setArticleId(response.getBody().getId());
            comment.setDateOfWriting(LocalDate.now());
            comment.setAuthor(principal.getName());
            commentService.saveComment(comment);

            return ResponseEntity.ok("Comment saved successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("State with title '" + title + "' not found");
        )
    }
}
