package org.martix.commentservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllState_Title(String title);
    CommentEntity findCommentEntityByText(String text);
    void removeCommentEntityById(Long id);
}
