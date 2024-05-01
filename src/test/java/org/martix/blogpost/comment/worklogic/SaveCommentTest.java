package org.martix.blogpost.comment.worklogic;

import org.junit.jupiter.api.Test;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.martix.blogpost.comments.worklogic.CommentController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveCommentTest {

    /**
     * Для макетов
     *
     * Здесь создаются макеты (mock) для StateService и CommentService,
     * а затем создается экземпляр контроллера CommentController с использованием этих макетов.
     * */
    private StateService stateService = mock(StateService.class);
    private CommentService commentService = mock(CommentService.class);
    private CommentController controller = new CommentController(stateService, commentService);

    /**
     * Для теста testSaveComment_StateFound()
     *
     * Этот метод-тест проверяет поведение метода saveComment(), когда состояние найдено.
     * Устанавливается заглушка для findStateByTitle(), чтобы он возвращал mockState.
     * Затем создается комментарий, вызывается метод saveComment() контроллера,
     * и проверяется, что комментарий сохранен в сервисе комментариев и возвращается ожидаемый ответ.
     * */
    @Test
    public void testSaveComment_StateFound() {
        String title = "TestTitle";
        StateEntity mockState = new StateEntity();
        when(stateService.findStateByTitle(title)).thenReturn(mockState);

        CommentEntity comment = new CommentEntity();
        comment.setText("Test comment");

        ResponseEntity<String> response = controller.saveComment(title, comment);

        verify(commentService, times(1)).saveComment(comment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Comment saved successfully", response.getBody());
    }

    /**
     * Для теста testSaveComment_StateNotFound()
     *
     * Этот метод-тест проверяет поведение метода saveComment(), когда состояние не найдено.
     * Устанавливается заглушка для findStateByTitle(), чтобы он возвращал null.
     * Затем вызывается метод saveComment() контроллера, и проверяется,
     * что комментарий не сохранен в сервисе комментариев, и возвращается ожидаемый ответ.
     * */
    @Test
    public void testSaveComment_StateNotFound() {
        String title = "Non-existent Title";
        when(stateService.findStateByTitle(title)).thenReturn(null);

        CommentEntity comment = new CommentEntity();
        comment.setText("Test comment");

        ResponseEntity<String> response = controller.saveComment(title, comment);

        verify(commentService, never()).saveComment(comment);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("State with title 'Non-existent Title' not found", response.getBody());
    }
}
