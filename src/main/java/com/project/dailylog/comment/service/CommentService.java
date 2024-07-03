package com.project.dailylog.comment.service;

import com.project.dailylog.board.domain.Board;
import com.project.dailylog.board.repository.BoardRepository;
import com.project.dailylog.comment.DTO.CommentDTO;
import com.project.dailylog.comment.domain.Comment;
import com.project.dailylog.comment.repository.CommentRepository;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO, Long boardId) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));

        Comment comment = Comment.builder()
                .commentContent(commentDTO.getCommentContent())
                .user(user)
                .board(board)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.toDto(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.updateContent(commentDTO.getCommentContent());
        return CommentDTO.toDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsByBoardId(Long boardId) {
        boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByBoardBoardId(boardId);
        return comments.stream()
                .map(CommentDTO::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        if (comment.getUser().getUserId() == userId) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("잘못된 접근입니다.");
        }
    }

    @Transactional
    public void deleteAllCommentsByBoard(Long boardId) {
        commentRepository.deleteByBoardBoardId(boardId);
    }
}
