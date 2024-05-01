/**
 * Не нужный файл
 * */

package org.martix.blogpost.admin.worklogic;

import org.martix.blogpost.admin.StateEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class StateServiceDAO{

    private final List<StateEntity> STATES = new ArrayList<>();

    public List<StateEntity> findAllStates() {
        return STATES;
    }

    public StateEntity saveState(StateEntity state) {
        STATES.add(state);
        return state;
    }

    public StateEntity findStateByTitle(String title) {
        return STATES.stream()
                .filter(state -> state.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public StateEntity updateState(StateEntity state) {
        var stateIndex = IntStream.range(0, STATES.size())
                .filter(index -> STATES.get(index).getTitle().equals(state.getTitle()))
                .findFirst()
                .orElse(-1);
        if (stateIndex > -1) {
            STATES.set(stateIndex, state);
            return state;
        }
        return null;
    }

    public void deleteState(String title) {
        var state = findStateByTitle(title);
        if(state != null) {
            STATES.remove(state);
        }
    }
}
