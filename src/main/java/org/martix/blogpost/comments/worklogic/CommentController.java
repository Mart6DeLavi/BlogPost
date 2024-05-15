package org.martix.blogpost.comments.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogpost")
@AllArgsConstructor
public class CommentController {

    private final StateService stateService;
    private final CommentService commentService;


    private static final String TITLE = "/{title}";
    private static final String WRITE_AND_SAVE_COMMENT = "/{title}/write_comment";
    private static final String FIND_COMMENT_BY_TEXT = "/{title}/{text}/find_comment";

    /**
     * TODO: сделать чтобы в таблице comment_entity показывался заголовок статьи к которой привязан комментарий
     * */


    /**
     * For the get All Comments From State By Title() function,
     * this function takes the title of the article as a parameter
     * from the request URL.
     *
     * First to the article variable that is responsible for the article.
     * Then there is a check to see if the article has been found. If yes,
     * then all the comments adjacent to the article are taken from the article.
     *
     * * If this article is not found, then an exception is thrown about not finding the article
     * */
    @GetMapping(TITLE)
    public List<CommentEntity> getAllCommentsFromStateByTitle(@PathVariable String title) {
        var article = stateService.findStateByTitle(title);
        if(article != null) {
            return commentService.getAllCommentsByTitle(title);
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * For the saveComment() function
     *
     * This function accepts as parameters:
     * the title of the article of the String type and a comment of the CommentEntity type.
     * The title of the article is taken from the URL.
     *
     * First, to the variable article, which is responsible for the article.
     * Then there is a check to see if the article has been found. If yes:
     *
     * 1. First, the comment is established that it refers to the article 'article' through this method: comment.setState(article);
     *
     * 2. After the relationship is established, a date is set for the comment,
     * the author is in this memory through the method: comment.set the Date of Writing(Local date.now());
     *
     * 3. After setting the date, the comment is saved to the database using this method: commentService.saveComment(comment);
     *
     * 4. If everything is successful, ResponseEntity.ok is sent and a message about the successful operation
     * */
    @PostMapping(WRITE_AND_SAVE_COMMENT)
    public ResponseEntity<String> saveComment(@PathVariable String title,
                                              @RequestBody CommentEntity comment,
                                              Principal principal) {
        var article = stateService.findStateByTitle(title);
        //System.out.println("State title: " + article);
        if(article != null) {
            comment.setState(article);
            comment.setDateOfWriting(LocalDate.now());
            commentService.saveComment(comment);
            return ResponseEntity.ok("Comment saved successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("State with title '" + title + "' not found");
        }
    }

    /**
     * For the find Comment By Text() function
     *
     * This function takes as parameters:
     * 1. The title of the article is of the String type.
     * 2. The text itself that needs to be replaced with a String type.
     * The title of the article and the text itself are taken from the URL.
     *
     * First, to the variable article, which is responsible for the article.
     * Then there is a check to see if the article has been found. If yes:
     *
     * 1. First, all comments are loaded into the 'comments' variable of the List<Comment Entity> type.
     * 2. After uploading, all comments are filtered by the text (received from the URL)
     * through the Stream API method .filter().
     * 3. All sorted comments are collected in a list using the method: .collect(Collectors.ToList()); and are given to the user
     * */
    @GetMapping(FIND_COMMENT_BY_TEXT)
    public List<CommentEntity> findCommentByText(@PathVariable("title") String title, @PathVariable("text") String text) {
        var article = stateService.findStateByTitle(title);
        if (article != null) {
            List<CommentEntity> comments = commentService.getAllCommentsByTitle(title);
            return comments.stream()
                    .filter(comment -> comment.getText().toLowerCase().contains(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
