package org.martix.blogpost.comments.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.ArticleService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.martix.blogpost.messages.EmailSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ArticleService artcileService;
    private final CommentService commentService;


    private static final String TITLE = "/{title}";
    private static final String WRITE_AND_SAVE_COMMENT = "/{title}/write_comment";
    private static final String FIND_COMMENT_BY_TEXT = "/{title}/{text}/find_comment";
    private final EmailSenderService emailSenderService;

    /**
     * TODO: сделать чтобы в таблице comment_entity показывался заголовок статьи к которой привязан комментарий
     * */


    /**
     * Handles GET requests to fetch all comments from a state by title.
     * This is achieved through the {@code @GetMapping} annotation which maps HTTP GET requests onto this method.
     *
     * @param title The title of the state. This is included in the path variable.
     * @return List of all comments from the state as {@link CommentEntity} objects. If the state does not exist, an empty list is returned.
     *
     * @see CommentEntity
     * @see ArticleService#findStateByTitle(String)
     * @see CommentService#getAllCommentsByTitle(String)
     */
    @GetMapping(TITLE)
    public List<CommentEntity> getAllCommentsFromStateByTitle(@PathVariable String title) {
        var article = artcileService.findStateByTitle(title);
        if(article != null) {
            return commentService.getAllCommentsByTitle(title);
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Для функции saveComment()
     *
     * Данная функция в качестве параметров принимает:
     * название статьи типа String и комментарий типа CommentEntity.
     * Название статьи берёт с URL адреса.
     *
     * Сначала в переменную article которая отвечает за статью.
     * Потом идёт проверка нашлась ли статья. Если да:
     *
     * 1. Сначала у комментария устанавливается, что он относится к статье 'article' через данный метод: comment.setState(article);
     *
     * 2. После установки отношений у комментария ставится дата,
     * которая есть в данный момент через метод: comment.setDateOfWriting(LocalDate.now());
     *
     * 3. После установки даты, комментарий сохраняется в базу данных через данный метод: commentService.saveComment(comment);
     *
     * 4. Если всё успешно - отправляется ResponseEntity.ok и сообщение об успешной операции
     * */

    /**
     * Handles POST requests to save a comment for a state.
     * This is achieved through the {@code @PostMapping} annotation which maps HTTP POST requests onto this method.
     *
     * @param title The title of the state. This is included in the path variable.
     * @param comment The comment to be saved as a {@link CommentEntity} object. This is included in the request body.
     * @param principal The currently authenticated user. This is used to set the author of the comment.
     * @return A {@link ResponseEntity} indicating the result of the operation. If the state does not exist, a NOT_FOUND status is returned.
     *
     * @see CommentEntity
     * @see ArticleService#findStateByTitle(String)
     * @see CommentService#saveComment(CommentEntity)
     * @see EmailSenderService#sendEmail(String, String, String)
     */
    @PostMapping(WRITE_AND_SAVE_COMMENT)
    public ResponseEntity<?> saveComment(@PathVariable String title,
                                              @RequestBody CommentEntity comment,
                                              Principal principal) {
        var article = artcileService.findStateByTitle(title);
        if(article != null) {
            comment.setState(article);
            comment.setDateOfWriting(LocalDate.now());
            comment.setAuthor(principal.getName());
            commentService.saveComment(comment);

            emailSenderService.sendEmail("azmater59034@gmail.com",
                    "New comment from: " + comment.getAuthor(),
                    "You have a new comment under your article: \n\n" + comment.getText());

            return ResponseEntity.ok("Comment saved successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("State with title '" + title + "' not found");
        }
    }

    /**
     * Для функции findCommentByText()
     *
     * Данная функция в качестве параметров принимает:
     * 1. Название статьи типа String.
     * 2. Сам текст который надо заменить типа String.
     * Название статьи и сам текст берутся из URL адреса.
     *
     * Сначала в переменную article которая отвечает за статью.
     * Потом идёт проверка нашлась ли статья. Если да:
     *
     * 1. Сначала все комментарии загружаются в переменную 'comments' типа List<CommentEntity>.
     * 2. После загрузки проводится фильтрация всех комментариев по тексту (полученного с URL адреса)
     * через метод Stream API .filter().
     * 3. Все отсортированные комментарии собираются в список через метод: .collect(Collectors.toList()); и выдаются пользователю
     * */

    /**
     * Handles GET requests to find comments by text from a state by title.
     * This is achieved through the {@code @GetMapping} annotation which maps HTTP GET requests onto this method.
     *
     * @param title The title of the state. This is included in the path variable.
     * @param text The text to search for in the comments. This is included in the path variable.
     * @return List of comments containing the search text from the state as {@link CommentEntity} objects. If the state does not exist, null is returned.
     *
     * @see CommentEntity
     * @see ArticleService#findStateByTitle(String)
     * @see CommentService#getAllCommentsByTitle(String)
     */
    @GetMapping(FIND_COMMENT_BY_TEXT)
    public List<CommentEntity> findCommentByText(@PathVariable("title") String title, @PathVariable("text") String text) {
        var article = artcileService.findStateByTitle(title);
        if (article != null) {
            List<CommentEntity> comments = commentService.getAllCommentsByTitle(title);
            return comments.stream()
                    .filter(comment -> comment.getText().toLowerCase().contains(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Handles DELETE requests to delete a comment by id.
     * This is achieved through the {@code @DeleteMapping} annotation which maps HTTP DELETE requests onto this method.
     *
     * @param id The id of the comment to be deleted. This is included in the path variable.
     * @return A string indicating the result of the operation.
     *
     * @see CommentService#deleteCommentById(Long)
     */

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return "Comment was successfully deleted";
    }
}
