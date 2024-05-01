package org.martix.blogpost;

import java.util.List;

public interface StateService {
    List<StateEntity> findAllStates();
    StateEntity saveState(StateEntity state);
    StateEntity findStateByTitle(String title);
    StateEntity updateState(StateEntity state);
    void deleteState(String title);

}
