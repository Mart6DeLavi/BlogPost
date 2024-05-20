package org.martix.blogpost.comments.worklogic;

import lombok.AllArgsConstructor;
import org.martix.blogpost.comments.CommentEntity;
import org.martix.blogpost.comments.CommentRepository;
import org.martix.blogpost.comments.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    /**
     * Method to retrieve all comments associated with a specific title from the repository.
     * @param title The title associated with the comments.
     * @return A list of all comments associated with the given title.
     */
    @Override
    public List<CommentEntity> getAllCommentsByTitle(String title) {
        return commentRepository.findAllByState_Title(title);
    }

    /**
     * Method to save a comment in the repository.
     * @param comment The comment to be saved.
     */
    @Override
    public void saveComment(CommentEntity comment) {
        commentRepository.save(comment);
    }

    /**
     * Method to find a comment by its text.
     * @param text The text of the comment.
     * @return The comment with the given text.
     */
    @Override
    public CommentEntity findCommentByText(String text) {
        return commentRepository.findCommentByText(text);
    }

    /**
     * Method to delete a comment by its ID.
     * @param id The ID of the comment to be deleted.
     */
    @Override
    public void deleteCommentById(Long id) {
        commentRepository.removeCommentEntityById(id);
    }
}
