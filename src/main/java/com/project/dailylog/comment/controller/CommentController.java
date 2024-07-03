package com.project.dailylog.comment.controller;

import com.project.dailylog.comment.DTO.CommentDTO;
import com.project.dailylog.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/read/{boardId}")
    public ResponseEntity<List<CommentDTO>> readCommentList(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(commentService.getAllCommentsByBoardId(boardId));
    }

    @PostMapping("/create/{boardId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("boardId") Long boardId,
                                                    @RequestBody CommentDTO commentDTO) {
        CommentDTO saveComment = commentService.createComment(commentDTO, boardId);
        return ResponseEntity.ok(saveComment);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentDTO));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId,
                                           @RequestBody CommentDTO commentDTO) {
        commentService.deleteComment(commentId, commentDTO.getUserId());
        return ResponseEntity.ok("삭제 성공");
    }
}
