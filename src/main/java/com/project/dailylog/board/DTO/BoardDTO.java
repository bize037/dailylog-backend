package com.project.dailylog.board.DTO;

import com.project.dailylog.board.domain.Board;
import com.project.dailylog.sign.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String boardType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String email;
    private Long userId;

    private final LocalDateTime currentTime = LocalDateTime.now();

    public Board toEntity(User user) {
        return Board.builder()
                .title(title)
                .content(content)
                .boardType(boardType)
                .createdDate(currentTime)
                .modifiedDate(currentTime)
                .user(user)
                .build();
    }

    public static BoardDTO toDto(Board board) {
        return BoardDTO.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .boardType(board.getBoardType())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .userId(board.getUser().getUserId())
                .build();
    }
}
