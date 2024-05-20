package org.martix.blogpost.admin.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.admin.ArticleEntity;
import org.martix.blogpost.admin.ArticleRepository;
import org.martix.blogpost.admin.ArticleService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * Method to retrieve all states from the repository.
     * @return A list of all states.
     */
    @Override
    public List<ArticleEntity> findAllStates() {
        return articleRepository.findAll();
    }

    /**
     * Method to save a state in the repository.
     * @param state The state to be saved.
     */
    @Override
    public void saveState(ArticleEntity state) {
        articleRepository.save(state);
    }

    /**
     * Method to find a state by its title.
     * @param title The title of the state.
     * @return The state with the given title.
     */
    @Override
    public ArticleEntity findStateByTitle(String title) {
        return articleRepository.findStateByTitle(title);
    }

    /**
     * Method to update a state.
     * @param state The state to be updated.
     * @return The updated state.
     */
    @Override
    public ArticleEntity updateState(ArticleEntity state) {
        return articleRepository.save(state);
    }

    /**
     * Method to delete a state by its title.
     * @param title The title of the state to be deleted.
     */
    @Override
    @Transactional
    public void deleteState(String title) {
        articleRepository.deleteByTitle(title);
    }
}
