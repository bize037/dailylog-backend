package com.project.dailylog.board.DTO;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class EditBoardDTO {
    private String title;
    private String content;
    private String boardType;
    private LocalDateTime modifiedDate;
    private String email;
}
