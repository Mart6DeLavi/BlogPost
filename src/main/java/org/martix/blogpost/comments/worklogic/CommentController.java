package org.martix.blogpost.comments.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blogpost/state")
@AllArgsConstructor
public class CommentController {

    private final StateService stateService;
    private final CommentService commentService;

    /**
     * TODO: сделать чтобы в таблице comment_entity показывался заголовок статьи к которой привязан комментарий
     * */


    /**
     * Для функции getAllCommentsFromStateByTitle()
     *
     * Данная функция как параметр принимает заголовок статьи
     * из URL запроса.
     *
     * Сначала в переменную article которая отвечает за статью.
     * Потом идёт проверка нашлась ли статья. Если да,
     * то со статьи берутся все прилегающие к ней комментарии.
     *
     * Если данная статья не найдена, тогда выбрасывается исключение про ненахождение статьи
     * */
    @GetMapping("/{title}")
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
    @PostMapping("/{title}/save_comment")
    public ResponseEntity<String> saveComment(@PathVariable String title, @RequestBody CommentEntity comment) {
        var article = stateService.findStateByTitle(title);
        System.out.println("State title: " + article);
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
    @GetMapping("/{title}/find_comment/{text}")
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
