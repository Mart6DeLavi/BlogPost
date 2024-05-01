package org.martix.blogpost.comments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    public List<CommentEntity> getAllCommentsByTitle(String title) {
        return commentRepository.findAllByState_Title(title);
    }

    @Override
    public void saveComment(CommentEntity comment) {
        commentRepository.save(comment);
    }

    @Override
    public CommentEntity findCommentByText(String text) {
        return commentRepository.findCommentByText(text);
    }

    /*@Override
    public CommentEntity updateComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteCommentById(id);
    }*/
}
