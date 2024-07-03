package com.project.dailylog.board.service;

import com.project.dailylog.board.DTO.BoardDTO;
import com.project.dailylog.board.DTO.EditBoardDTO;
import com.project.dailylog.board.domain.Board;
import com.project.dailylog.board.repository.BoardRepository;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public BoardDTO writeBoard(BoardDTO BoardDTO) {
        User user = userRepository.findByEmail(BoardDTO.getEmail()).get();
        Board board = BoardDTO.toEntity(user);
        return BoardDTO.toDto(boardRepository.save(board));
    }

    @Transactional(readOnly = true)
    public BoardDTO readBoard(Long boardId) {
        Board boardInfo = boardRepository.findByBoardId(boardId).get();
        return BoardDTO.toDto(boardInfo);
    }

    @Transactional
    public EditBoardDTO editBoard(EditBoardDTO editBoardDTO, long boardId) {
        boardRepository.editBoard(editBoardDTO.getBoardType(), editBoardDTO.getContent(), editBoardDTO.getTitle(),
                LocalDateTime.now(), boardId);
        return editBoardDTO;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.delete(boardRepository.findByBoardId(boardId).get());
    }

    @Transactional(readOnly = true)
    public List<BoardDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(BoardDTO::toDto)
                .collect(Collectors.toList());
    }

    private BoardDTO convertToDto(Board board) {
        return modelMapper.map(board, BoardDTO.class);
    }
}
