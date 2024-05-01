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
}
