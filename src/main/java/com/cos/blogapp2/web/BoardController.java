package com.cos.blogapp2.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {	// title, content
		
		Board board = dto.toEntity();
		boardRepository.save(board);
		
		return "redirect:/";
	}
	
	@GetMapping("/board")
	public String list(Model model, int page) {
		page--;
		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("id").descending());
		
		Page<Board> boardsEntity = boardRepository.findAll(pageRequest);	// Entity = DB에서 가져온것
		model.addAttribute("boardsEntity", boardsEntity);
		
		System.out.println(boardsEntity);
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id) {
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		return "board/saveForm";
	}
}
