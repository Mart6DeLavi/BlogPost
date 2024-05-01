package org.martix.blogpost.admin.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogpost/states")
@AllArgsConstructor
public class StateController {
    private final StateService stateService;

    //Получение всех статей
    @GetMapping
    public List<StateEntity> findAllStates() {
        return stateService.findAllStates();
    }

    //Сохранение статьи
    @PostMapping("save_state")
    public String saveState(@RequestBody StateEntity state) {
        stateService.saveState(state);
        return "Saved";
    }

    //Нахождение статьи по заголовку
    @GetMapping("/find_state/{title}")
    public StateEntity findStateByTitle(@PathVariable String title) {
        return stateService.findStateByTitle(title);
    }

    //Изменение статьи
    @PutMapping("update_state")
    public StateEntity updateState(@RequestBody StateEntity state) {
        return stateService.updateState(state);
    }

    //Удаление статьи
    @DeleteMapping("/delete_state/{title}")
    public void deleteState(@PathVariable String title) {
        stateService.deleteState(title);
    }
}
