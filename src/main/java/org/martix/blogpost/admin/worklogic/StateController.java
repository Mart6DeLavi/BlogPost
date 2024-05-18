package org.martix.blogpost.admin.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogpost")
@AllArgsConstructor
public class StateController {
    private final StateService stateService;

    private static final String SAVE_STATE = "/save_state";
    private static final String FIND_STATE_BY_TITLE = "/{title}/find_state";
    private static final String UPDATE_STATE = "/update_state";
    private static final String DELETE_STATE_BY_TITLE = "/{title}/delete_state";

    //Получение всех статей
    @GetMapping
    public List<StateEntity> findAllStates() {
        return stateService.findAllStates();
    }

    //Сохранение статьи
    @PostMapping(SAVE_STATE)
    public String saveState(@RequestBody StateEntity state) {
        stateService.saveState(state);
        return "Saved";
    }

    //TODO:переделать, чтобы искалась статья не по полному заголовку, а по сходству слов в заголовке
    //Нахождение статьи по заголовку
    @GetMapping(FIND_STATE_BY_TITLE)
    public StateEntity findStateByTitle(@PathVariable String title) {
        return stateService.findStateByTitle(title);
    }

    //Изменение статьи
    @PutMapping(UPDATE_STATE)
    public StateEntity updateState(@RequestBody StateEntity state) {
        return stateService.updateState(state);
    }

    //Удаление статьи
    @DeleteMapping(DELETE_STATE_BY_TITLE)
    public void deleteState(@PathVariable String title) {
        stateService.deleteState(title);
    }
}
