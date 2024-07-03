package com.project.dailylog.board.repository;

import com.project.dailylog.board.domain.Board;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByBoardId(Long BoardId);

    @Modifying
    @Query("UPDATE Board b SET b.boardType = :boardType, b.content = :content, b.title = :title, b.modifiedDate = :modifiedDate WHERE b.boardId = :boardId")
    void editBoard(@Param("boardType") String boardType, @Param("content") String content, @Param("title") String title,
                     @Param("modifiedDate") LocalDateTime modifiedDate, @Param("boardId") long boardId);
}
