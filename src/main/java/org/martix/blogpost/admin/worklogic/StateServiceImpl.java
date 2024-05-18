package org.martix.blogpost.admin.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.StateEntity;
import org.martix.blogpost.admin.StateRepository;
import org.martix.blogpost.admin.StateService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    @Override
    public List<StateEntity> findAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public void saveState(StateEntity state) {
        stateRepository.save(state);
    }

    @Override
    public StateEntity findStateByTitle(String title) {
        return stateRepository.findStateByTitle(title);
    }

    @Override
    public StateEntity updateState(StateEntity state) {
        return stateRepository.save(state);
    }

    @Override
    @Transactional
    public void deleteState(String title) {
        stateRepository.deleteByTitle(title);
    }
}
