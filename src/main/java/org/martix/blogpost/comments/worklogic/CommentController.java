package org.martix.blogpost.comments;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/blogpost/state")
@AllArgsConstructor
public class CommentController {

    private final StateService stateService;
    private final CommentService commentService;

    /**
     * TODO: сделать чтобы функция возвращала все комментарии, которые написаны к данной статье
     * TODO: сделать чтобы в таблице comment_entity показывался заголовок статьи к которой привязан комментарий
     * */
    //РАБОТАЕТ
    @GetMapping("/{title}")
    public List<CommentEntity> getAllCommentsFromStateByTitle(@PathVariable String title) {
        var state = stateService.findStateByTitle(title);
        if(state != null) {
            return commentService.getAllCommentsByTitle(title);
        }
        else {
            return Collections.emptyList();
        }
    }

    //РАБОТАЕТ
    @PostMapping("/{title}/save_comment")
    public ResponseEntity<String> saveComment(@PathVariable String title, @RequestBody CommentEntity comment) {
        var stateTitle = stateService.findStateByTitle(title);
        System.out.println("State title: " + stateTitle);
        if(stateTitle != null) {
            comment.setState(stateTitle);
            comment.setDateOfWriting(LocalDate.now());
            commentService.saveComment(comment);
            return ResponseEntity.ok("Comment saved successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("State with title '" + title + "' not found");
        }
    }

    //РАБОТАЕТ
    @GetMapping("/{title}/find_comment/{text}")
    public List<CommentEntity> findCommentByText(@PathVariable("title") String title, @PathVariable("text") String text) {
        var state = stateService.findStateByTitle(title);
        if (state != null) {
            List<CommentEntity> comments = commentService.getAllCommentsByTitle(title);
            return comments.stream()
                    .filter(comment -> comment.getText().toLowerCase().contains(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }


    /*@PutMapping("/{title}/{text_now}/update_comment")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable("title") String title, @PathVariable("text_now") String textNow, @RequestBody CommentEntity updatedComment) {
        var state = stateService.findStateByTitle(title);
        System.out.println("State: " + state);
        if(state != null) {
            var comment = commentService.findCommentByText(textNow);
            System.out.println("Comment: " + comment);
            if(comment != null) {
                comment.setText(updatedComment.getText());
                commentService.saveComment(updatedComment);
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    /*@DeleteMapping("/{title}/delete_comment")
    public void deleteComment(@PathVariable String title, long id) {
        var state = stateService.findStateByTitle(title);
        if(state != null) {
            List<CommentEntity> comments = commentService.getAllCommentsByTitle(title);
            var commentId = IntStream.range(0, comments.size())
                    .filter(index -> comments.get(index).getId().equals(id))
                    .findFirst()
                    .orElse(-1);
            if(commentId > -1) {
                commentService.deleteComment(commentId);
            }
        }
    }*/

}
