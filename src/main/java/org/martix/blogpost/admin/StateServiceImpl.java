package org.martix.blogpost.admin;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class StateServiceImpl implements StateService{
    private final StateRepository stateRepository;

    @Override
    public List<StateEntity> findAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public StateEntity saveState(StateEntity state) {
        return stateRepository.save(state);
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
