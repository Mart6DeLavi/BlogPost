package org.martix.blogpost.comments;

import java.util.List;
public interface CommentService {
    List<CommentEntity> getAllCommentsByTitle(String title);
    void saveComment(CommentEntity comment);
    CommentEntity findCommentByText(String text);
}
