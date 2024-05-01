package org.martix.blogpost.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@AllArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping("/all")
    public List<StateEntity> findAllStates() {
        return stateService.findAllStates();
    }

    @PostMapping("save_state")
    public String saveState(@RequestBody StateEntity state) {
        stateService.saveState(state);
        return "Saved";
    }

    @GetMapping("/find_state/{title}")
    public StateEntity findStateByTitle(@PathVariable String title) {
        return stateService.findStateByTitle(title);
    }

    @PutMapping("update_state")
    public StateEntity udpateState(@RequestBody StateEntity state) {
        return stateService.updateState(state);
    }

    @DeleteMapping("/delete_state/{title}")
    public void deleteState(@PathVariable String title) {
        stateService.deleteState(title);

    }
}
