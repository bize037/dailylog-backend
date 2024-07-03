package com.project.dailylog.comment.DTO;

import com.project.dailylog.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private String commentContent;
    private Long userId;
    private Long boardId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private static LocalDateTime currentDate = LocalDateTime.now();

    public static CommentDTO toDto(Comment comment) {
        return CommentDTO.builder()
                .commentContent(comment.getCommentContent())
                .userId(comment.getUser().getUserId())
                .boardId(comment.getBoard().getBoardId())
                .createdDate(currentDate)
                .modifiedDate(currentDate)
                .build();
    }
}
