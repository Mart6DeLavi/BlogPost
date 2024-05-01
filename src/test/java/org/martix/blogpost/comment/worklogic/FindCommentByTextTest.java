package org.martix.blogpost.comment.worklogic;

import org.junit.jupiter.api.Test;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.martix.blogpost.comments.worklogic.CommentController;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindCommentByTextTest {

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
     * Для теста testFindCommentByText_StateFound()
     *
     * Этот метод-тест проверяет поведение метода findCommentByText(),
     * когда состояние найдено и есть совпадающие комментарии.
     * Устанавливается заглушка для findStateByTitle(), чтобы он возвращал mockState.
     * Затем устанавливаются ожидаемые результаты вызова getAllCommentsByTitle(),
     * и проверяется, что возвращенное количество комментариев соответствует ожидаемому.
     * */
    @Test
    public void testFindCommentByText_StateFound() {
        String title= "TestTitle";
        String searchText = "test";

        StateEntity mockState = new StateEntity();
        when(stateService.findStateByTitle(title)).thenReturn(mockState);

        CommentEntity comment1 = new CommentEntity();
        comment1.setText("This is a test comment");

        CommentEntity comment2 = new CommentEntity();
        comment2.setText("Another test comment");

        when(commentService.getAllCommentsByTitle(title)).thenReturn(Arrays.asList(comment1, comment2));
        List<CommentEntity> result = controller.findCommentByText(title, searchText);
        assertEquals(2, result.size());
    }


    /**
     * Для теста testFindCommentByText_StateNotFound()
     *
     * Этот метод-тест проверяет поведение метода findCommentByText(), когда состояние не найдено.
     * Устанавливается заглушка для findStateByTitle(), чтобы он возвращал null.
     * Проверяется, что возвращается null
     * */
    @Test
    public void testFindCommentByText_StateNotFound() {
        String title= "TestTitle";
        String searchText = "test";

        when(stateService.findStateByTitle(title)).thenReturn(null);
        List<CommentEntity> result = controller.findCommentByText(title, searchText);
        assertNull(result);
    }

    /**
     * Для теста testFindCommentByText_NoMatchingComments()
     *
     * Этот метод-тест проверяет поведение метода findCommentByText(),
     * когда состояние найдено, но нет совпадающих комментариев.
     * Устанавливается заглушка для findStateByTitle() и getAllCommentsByTitle(),
     * чтобы они возвращали предопределенные значения.
     * Проверяется, что возвращается пустой список комментариев
     * */
    @Test
    public void testFindCommentByText_NoMatchingComments() {
        String title= "TestTitle";
        String searchText = "foo";

        StateEntity mockState = new StateEntity();
        when(stateService.findStateByTitle(title)).thenReturn(mockState);

        CommentEntity comment1 = new CommentEntity();
        comment1.setText("This is a test comment");

        CommentEntity comment2 = new CommentEntity();
        comment2.setText("Another test comment");

        when(commentService.getAllCommentsByTitle(title)).thenReturn(Arrays.asList(comment1, comment2));
        List<CommentEntity> result = controller.findCommentByText(title, searchText);
        assertEquals(0, result.size());
    }
}
