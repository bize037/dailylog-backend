package com.project.dailylog.board.controller;

import com.project.dailylog.board.DTO.BoardDTO;
import com.project.dailylog.board.DTO.DeleteFileDTO;
import com.project.dailylog.board.DTO.EditBoardDTO;
import com.project.dailylog.board.service.BoardService;
import com.project.dailylog.board.service.S3Service;
import com.project.dailylog.comment.service.CommentService;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final S3Service s3Service;

    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> showAllBoard() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @PostMapping("/post")
    public ResponseEntity<BoardDTO> write(@RequestBody BoardDTO BoardDTO) {
        BoardDTO saveBoard = boardService.writeBoard(BoardDTO);
        return ResponseEntity.ok(saveBoard);
    }

    @GetMapping("/post/{boardId}")
    public ResponseEntity<BoardDTO> read(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.readBoard(boardId));
    }

    @GetMapping("/edit/{boardId}")
    public ResponseEntity<BoardDTO> editRead(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.readBoard(boardId));
    }

    @PutMapping("/edit/{boardId}")
    public ResponseEntity<EditBoardDTO> edit(@PathVariable("boardId") Long boardId,
                                             @RequestBody EditBoardDTO editBoardDTO) {
        return ResponseEntity.ok(boardService.editBoard(editBoardDTO, boardId));
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> delete(@PathVariable("boardId") Long boardId) {
        commentService.deleteAllCommentsByBoard(boardId);
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    @PostMapping("/uploadFile/{boardId}")
    public ResponseEntity<?> submitFiles (@PathVariable("boardId") Long boardId, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFileList) throws IOException {
        return ResponseEntity.ok(s3Service.submitFiles(boardId, multipartFileList));
    }

    @DeleteMapping("/uploadFile/delete")
    public ResponseEntity<?> deleteFiles(@RequestBody DeleteFileDTO deleteFileDTO) {
        s3Service.deleteFile(deleteFileDTO.getKeyName());
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
