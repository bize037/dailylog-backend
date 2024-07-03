package com.project.dailylog.board.controller;

import com.project.dailylog.board.DTO.LikeDTO;
import com.project.dailylog.board.service.LikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{boardId}")
    public ResponseEntity<?> likeBoard(@PathVariable("boardId") Long boardId, @RequestBody LikeDTO likeDTO) {
        likeService.likeBoard(likeDTO.getUserEmail(), boardId);
        return ResponseEntity.ok("좋아요 표시");
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<Long>> getLikeBoard(@PathVariable("boardId") Long boardId) {
        likeService.getLikeList(boardId);
        return ResponseEntity.ok(likeService.getLikeList(boardId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> unlikeBoard(@PathVariable("boardId") Long boardId, @RequestBody LikeDTO likeDTO) {
        likeService.unlikeBoard(likeDTO.getUserEmail(), boardId);
        return ResponseEntity.ok("좋아요 해제");
    }

    @GetMapping("liked/{boardId}")
    public ResponseEntity<Boolean> isLikedByUser(@PathVariable("boardId") Long boardId, @RequestBody LikeDTO likeDTO) {
        boolean isLiked = likeService.isLikedByUser(likeDTO.getUserEmail(), boardId);
        return ResponseEntity.ok(isLiked);
    }
}
