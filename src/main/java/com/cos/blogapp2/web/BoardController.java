package com.cos.blogapp2.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.service.BoardService;
import com.cos.blogapp2.service.CommentService;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;
import com.cos.blogapp2.web.dto.CMRespDto;
import com.cos.blogapp2.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService boardService;
	private final CommentService commentService;
	private final HttpSession session;
	
	@PostMapping("/api/board/{boardId}/comment")
	public @ResponseBody CMRespDto<?> commentSave(@PathVariable int boardId,@Valid @RequestBody CommentSaveReqDto dto, BindingResult bindingResult) {
		User principal = (User) session.getAttribute("principal");
		// 값 검사
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " + error.getField());
				System.out.println("메시지 : " + error.getDefaultMessage());
				System.out.println();
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}
		
		commentService.댓글쓰기(boardId, dto, principal);
		
		return new CMRespDto<>(1, "댓글 작성 성공", null);
	}
	
	@DeleteMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		boardService.게시글삭제(id, principal);
		
		return new CMRespDto<>(1, "삭제 완료", null);
	}
	
	@PutMapping("/api/board/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id,@Valid @RequestBody BoardSaveReqDto dto, BindingResult bindingResult) {	// @requestBody = 오브젝트(json)의 데이터를 받을때 사용
		User principal = (User) session.getAttribute("principal");
		boardService.게시글수정(id, dto, principal);
		
		return new CMRespDto<>(1, "수정 완료", null);
	}
	
	@GetMapping("/api/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		User principal = (User) session.getAttribute("principal");
		Board boardEntity = boardService.게시글수정페이지이동(id);
		
		if(principal.getId() != boardEntity.getUser().getId())
			throw new MyNotFoundException("권한이 없습니다.");
		model.addAttribute("boardEntity", boardEntity);
		
		return "board/updateForm";
	}
	
	@PostMapping("/api/board")
	public String save(BoardSaveReqDto dto) {
		User principal = (User) session.getAttribute("principal");
		boardService.게시글생성(dto, principal);
		
		return "redirect:/";
	}
	
	@GetMapping("/board")
	public String list(Model model, int page) {
		page--;
		PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("id").descending());
		
		Page<Board> boardsEntity = boardService.게시글목록(pageRequest);
		model.addAttribute("boardsEntity", boardsEntity);
		
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		Board boardEntity = boardService.게시글상세보기(id);
		model.addAttribute("boardEntity",boardEntity);
		
		return "board/detail";
	}
	
	@GetMapping("/api/board/saveForm")
	public String boardSaveForm() {
		return "board/saveForm";
	}
}
