package org.martix.commentservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    @Override
    public List<CommentEntity> getAllCommentsByTitle(String title) {
        return commentRepository.findAllState_Title(title);
    }

    @Override
    public void saveComment(CommentEntity comment) {
        commentRepository.save(comment);
    }

    @Override
    public CommentEntity findCommentByText(String text) {
        return commentRepository.findCommentEntityByText(text);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.removeCommentEntityById(id);
    }
}
