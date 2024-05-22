package org.martix.commentservice;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentEntity> getAllCommentsByTitle(String title);
    void saveComment(CommentEntity comment);
    CommentEntity findCommentByText(String text);
    void deleteComment(Long id);
}
