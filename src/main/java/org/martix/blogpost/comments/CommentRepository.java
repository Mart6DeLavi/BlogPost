package org.martix.blogpost.comments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByState_Title(String title);
    CommentEntity findCommentByText(String text);
}
