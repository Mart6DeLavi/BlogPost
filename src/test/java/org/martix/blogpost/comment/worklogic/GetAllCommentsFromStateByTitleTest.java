package org.martix.blogpost.comment.worklogic;

import org.junit.jupiter.api.Test;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentService;
import org.martix.blogpost.comments.worklogic.CommentController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetAllCommentsFromStateByTitleTest {
    /**
     * Здесь создаются макеты (mock) для StateService и CommentService.
     * Это делается с помощью Mockito для изоляции тестируемого метода от зависимостей
     * и обеспечения контролируемой среды тестирования.
     * Затем создается экземпляр контроллера CommentController с использованием макетов сервисов.
     * */
    private StateService stateService = mock(StateService.class);
    private CommentService commentService = mock(CommentService.class);
    private CommentController controller = new CommentController(stateService, commentService);


    /**
     * Для теста testGetAllCommentsFromStateByTitle()
     * Этот метод-тест проверяет поведение метода getAllCommentsFromStateByTitle(), когда состояние найдено.
     * Создается заглушка mockState для StateEntity, которая будет возвращена при вызове findStateByTitle().
     * Также устанавливается ожидаемый результат вызова getAllCommentsByTitle().
     * Затем вызывается метод getAllCommentsFromStateByTitle() контроллера,
     * и его результат сравнивается с ожидаемым результатом.
     * */
    @Test
    public void testGetAllCommentsFromStateByTitle() {
        String title = "TestTitle";
        StateEntity mockState = new StateEntity();
        when(stateService.findStateByTitle(title)).thenReturn(mockState);

        List<CommentEntity> expectedComments = Arrays.asList(new CommentEntity(), new CommentEntity());
        when(commentService.getAllCommentsByTitle(title)).thenReturn(expectedComments);

        List<CommentEntity> actualComments = controller.getAllCommentsFromStateByTitle(title);

        assertEquals(expectedComments, actualComments);
    }

    /**
     * Для теста testGetAllCommentsFromStateByTitle_StateNotFound()
     *
     * Этот метод-тест проверяет поведение метода getAllCommentsFromStateByTitle(),
     * когда состояние не найдено. Устанавливается заглушка для findStateByTitle(),
     * чтобы он возвращал null, что означает, что состояние не найдено.
     *
     * Затем вызывается метод getAllCommentsFromStateByTitle() и утверждается,
     * что он возвращает пустой список комментариев.
     * */
    @Test
    public void testGetAllCommentsFromStateByTitle_StateNotFound() {
        String title = "Non-existent Title";
        when(stateService.findStateByTitle(title)).thenReturn(null);

        List<CommentEntity> actualComments = controller.getAllCommentsFromStateByTitle(title);

        assertEquals(Collections.emptyList(), actualComments);
    }
}
