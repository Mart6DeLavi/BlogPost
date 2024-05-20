package org.martix.blogpost.admin.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.ArticleEntity;
import org.martix.blogpost.admin.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/blogpost")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService artcileService;

    private static final String SAVE_STATE = "/save_state";
    private static final String FIND_STATE_BY_TITLE = "/{title}/find_state";
    private static final String UPDATE_STATE = "/update_state";
    private static final String DELETE_STATE_BY_TITLE = "/{title}/delete_state";

    //Получение всех статей

    /**
     * Handles GET requests to fetch all articles.
     *This is achieved through the {@code @GetMapping} annotation which maps
     * HTTP GET requests onto this method.
     * @return List of all articles as {@link ArticleEntity} objects.
     *
     * @see ArticleEntity
     * @see ArticleService#findAllStates()
     * */
    @GetMapping
    public List<ArticleEntity> findAllStates() {
        return artcileService.findAllStates();
    }

    //Сохранение статьи

    /**
     *Handles POST request to save an article.
     * This is achieved through the {@code @PostMapping} annotation which maps {@code HTTP POST} requests onto this method.
     *
     * @param  state This article to be saved as a {@link ArticleEntity} object. This is included in the request body.
     * @param principal The currently authenticated user. This is used to set the author of the article.
     *
     * @see ArticleEntity
     * @see ArticleService#saveState(ArticleEntity)
     *
     * @return {@code Saved} if article was saved successfully.
     * */
    @PostMapping(SAVE_STATE)
    public String saveState(@RequestBody ArticleEntity state,
                            Principal principal) {
        state.setAuthor(principal.getName());
        state.setCreatedAt(LocalDate.now());
        artcileService.saveState(state);
        return "Saved";
    }

    //TODO:переделать, чтобы искалась статья не по полному заголовку, а по сходству слов в заголовке
    //Нахождение статьи по заголовку

    /**
     * Handles GET request to find article by text in title. This achieved through {@code @PostMapping} annotation
     * which maps {@code HTTP GET} requests onto this method.
     *
     * @param title take name of article which user want to find. Need to get whole article title.
     *
     * @return article or articles which could possibility to find
     * */
    @GetMapping(FIND_STATE_BY_TITLE)
    public ArticleEntity findStateByTitle(@PathVariable String title) {
        return artcileService.findStateByTitle(title);
    }

    //Изменение стать
    @PutMapping(UPDATE_STATE)
    public ArticleEntity updateState(@RequestBody ArticleEntity state) {
        state.setUpdatedAt(LocalDate.now());
        return artcileService.updateState(state);
    }

    //Удаление статьи
    /**
     * @param title take text from user for article name which user want to delete
     * */
    @DeleteMapping(DELETE_STATE_BY_TITLE)
    public void deleteState(@PathVariable String title) {
        artcileService.deleteState(title);
    }
}
