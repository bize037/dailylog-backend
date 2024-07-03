package com.project.dailylog.comment.repository;

import com.project.dailylog.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardBoardId(Long boardId);

    void deleteByBoardBoardId(@Param("boardId") Long boardId);

    Comment findByCommentId(Long CommentId);
}
