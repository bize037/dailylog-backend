package com.project.dailylog.board.service;

import com.project.dailylog.board.domain.Board;
import com.project.dailylog.board.domain.Like;
import com.project.dailylog.board.repository.BoardRepository;
import com.project.dailylog.board.repository.LikeRepository;
import com.project.dailylog.common.constants.ErrorMessage;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void likeBoard(String userEmail, Long boardId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_BOARD.getMessage()));

        Like like = Like.builder()
                .user(user)
                .board(board)
                .build();

        likeRepository.save(like);
    }

    @Transactional
    public void unlikeBoard(String userEmail, Long boardId) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        likeRepository.deleteByUserUserIdAndBoardBoardId(user.get().getUserId(), boardId);
    }

    @Transactional(readOnly = true)
    public List<Long> getLikeList(Long boardId) {
        Board board = new Board(boardId);
        List<Like> likes = likeRepository.findByBoard(board);
        return likes.stream().map(like -> like.getUser().getUserId())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isLikedByUser(String userEmail, Long boardId) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return likeRepository.existsByUserUserIdAndBoardBoardId(user.get().getUserId(), boardId);
    }
}
