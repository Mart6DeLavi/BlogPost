package org.martix.blogpost.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByState_Title(String title);
    CommentEntity findCommentByText(String text);
    void removeCommentEntityById(Long id);
}
