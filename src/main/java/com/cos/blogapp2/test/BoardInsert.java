package com.cos.blogapp2.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardInsert {
	private final BoardRepository boardRepository;
	
	@GetMapping("/test/boardinsert")
	public String boardinsert() {
		List<Board> boards = new ArrayList<>();
		
		for(int i = 1; i < 2200 ; i++) {
			Board board = Board.builder()
					.title("글 제목 " + i)
					.content("내용 " + i)
					.build();
			boards.add(board);
		}
		boardRepository.saveAll(boards);
		
		return "redirect:/";
	}
	
}
