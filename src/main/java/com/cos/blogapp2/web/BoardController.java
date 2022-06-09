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
import com.cos.blogapp2.domain.board.BoardRepository;
import com.cos.blogapp2.domain.comment.Comment;
import com.cos.blogapp2.domain.comment.CommentRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.web.dto.BoardSaveReqDto;
import com.cos.blogapp2.web.dto.CMRespDto;
import com.cos.blogapp2.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final HttpSession session;
	
	
	@PostMapping("/board/{boardId}/comment")
	public @ResponseBody CMRespDto<?> commentSave(@PathVariable int boardId,@Valid @RequestBody CommentSaveReqDto dto, BindingResult bindingResult) {
		User principal = (User) session.getAttribute("principal");
		if (principal == null) { // 로그인 안됨
			return new CMRespDto<>(-1, "권한이 없습니다.", null);
		}
		Board boardEntity = boardRepository.findById(boardId)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
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
		
		Comment comment = Comment.builder()
				.content(dto.getContent())
				.user(principal)
				.board(boardEntity)
				.build();
		commentRepository.save(comment);
		
		return new CMRespDto<>(1, "댓글 작성 성공", null);
	}
	
	@DeleteMapping("/board/{id}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		
		if(boardEntity.getUser().getId() == principal.getId()) {
			boardRepository.deleteById(boardEntity.getId());
			return new CMRespDto<>(1, "삭제 완료", null);
		} else
			throw new MyAsyncNotFoundException("권한이 없습니다.");
	}
	
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id,@Valid @RequestBody BoardSaveReqDto dto, BindingResult bindingResult) {	// @requestBody = 오브젝트(json)의 데이터를 받을때 사용
		User principal = (User) session.getAttribute("principal");
		if (principal == null) { // 로그인 안됨
			throw new MyAsyncNotFoundException("로그인이 필요합니다.");
		}
		Board board = dto.toEntity();
		board.setId(id);
		board.setUser(principal);
		boardRepository.save(board);
		
		return new CMRespDto<>(1, "수정 완료", null);
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		User principal = (User) session.getAttribute("principal");
		
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		if(principal.getId() != boardEntity.getUser().getId())
			throw new MyNotFoundException("권한이 없습니다.");
		model.addAttribute("boardEntity", boardEntity);
		
		return "board/updateForm";
	}
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {	// title, content
		User principal = (User) session.getAttribute("principal");
		if (principal == null) { // 로그인 안됨
			throw new MyNotFoundException("로그인이 필요합니다.");
		}
		Board board = dto.toEntity();
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
		
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		
	//	Board boardEntity = boardRepository.findById(id).get();	// get() 옵셔널 안에 있는 걸 그대로 꺼내오는것. 없을경우의 오류를 잡지 못한다.
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException("게시글을 찾을수 없습니다.") );
		
		model.addAttribute("boardEntity",boardEntity);
		
		
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String boardSaveForm() {
		
		return "board/saveForm";
	}
}
