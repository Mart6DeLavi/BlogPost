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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveState(@RequestBody StateEntity state) {
        stateService.saveState(state);
        return "Saved";
    }

    //Нахождение статьи по заголовку
    @GetMapping(FIND_STATE_BY_TITLE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')&& hasAuthority('ROLE_USER')")
    public StateEntity findStateByTitle(@PathVariable String title) {
        return stateService.findStateByTitle(title);
    }

    //Изменение статьи
    @PutMapping(UPDATE_STATE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public StateEntity updateState(@RequestBody StateEntity state) {
        return stateService.updateState(state);
    }

    //Удаление статьи
    @DeleteMapping(DELETE_STATE_BY_TITLE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteState(@PathVariable String title) {
        stateService.deleteState(title);
    }
}
