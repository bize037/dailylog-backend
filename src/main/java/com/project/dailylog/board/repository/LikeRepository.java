package com.project.dailylog.board.repository;

import com.project.dailylog.board.domain.Board;
import com.project.dailylog.board.domain.Like;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserUserIdAndBoardBoardId(Long userId, Long boardId);
    boolean existsByUserUserIdAndBoardBoardId(Long userId, Long boardId);
    List<Like> findByBoard(Board board);
}
