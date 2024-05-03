package org.martix.blogpost.comments.worklogic;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/blogpost/state")
public class DeleteComment {
    private CommentService commentService;
    private StateService stateService;


    //TODO: сделать метод который будет удалять комментарии под статьями

    // @Getter
    private Map<Integer, String> sortedAndNumberedComments;

    @GetMapping("/{title}/{text}")
    public Map<Integer, String> getAndSortedList(@PathVariable String title, @PathVariable String text) {
        var article = stateService.findStateByTitle(title);
        if(article != null) {
            List<CommentEntity> allComments = commentService.getAllCommentsByTitle(title);
            sortedAndNumberedComments = IntStream.range(0, allComments.size())
                    .boxed()
                    .collect(Collectors.toMap(
                            i -> i + 1,
                            i -> allComments.get(i).getText()));
            return sortedAndNumberedComments;
        }
        return Collections.emptyMap();
    }


    @DeleteMapping("/{number}")
    public String deleteComment(@PathVariable int number) {
        if(number > 0 && number <= sortedAndNumberedComments.size()) {
            sortedAndNumberedComments.remove(number);
            return "Comment was successfully deleted";
        }
        return "Comment was not successfully deleted";
    }
}
