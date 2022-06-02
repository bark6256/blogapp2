package com.cos.blogapp2.web;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;
import com.cos.blogapp2.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final HttpSession session;
	
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @RequestBody BoardSaveReqDto dto) {	// @requestBody = 오브젝트(json)의 데이터를 받을때 사용
		
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("prinsipal");
		board.setId(id);
		board.setUser(principal);
		boardRepository.save(board);
		
		return new CMRespDto<>(1, "수정 완료", null);
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		Board boardEntity = boardRepository.findById(id).get();
		model.addAttribute("boardEntity", boardEntity);
		
		return "board/updateForm";
	}
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {	// title, content
		
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("principal");
		board.setUser(principal);
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
	public String detail(@PathVariable int id, Model model) {
		
		Board boardEntity = boardRepository.findById(id).get();	// get() 옵셔널 안에 있는 걸 그대로 꺼내오는것. 없을경우의 오류를 잡지 못한다.
		
		model.addAttribute("boardEntity",boardEntity);
		
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		
		return "board/saveForm";
	}
}
