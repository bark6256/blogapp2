package com.cos.blogapp2.test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PageTest {
	
	private final BoardRepository boardRepository;
	
	@GetMapping("/test")
	public Page<Board> list(int page) {
		System.out.println(page);
		page--;
		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by("id").descending());
		
		Page<Board> boardsEntity = boardRepository.findAll(pageRequest);	// Entity = DB에서 가져온것
		
		return boardsEntity;
	}
}
