package com.cos.blogapp2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.comment.Comment;
import com.cos.blogapp2.domain.comment.CommentRepository;
import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentRepository commentRepository;
	private final HttpSession session;
	
	@DeleteMapping("/comment/{id}")
	public @ResponseBody CMRespDto<?> commentDelete(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		if(principal == null)
			throw new MyAsyncNotFoundException("로그인이 필요합니다.");
		
		Comment commentEntity = commentRepository.findById(id)
				.orElseThrow(() -> new MyAsyncNotFoundException("댓글을 찾지 못했습니다."));
		if(commentEntity.getUser().getId() == principal.getId()) {
			commentRepository.deleteById(id);
			return new CMRespDto<>(1, "댓글 삭제 성공", null);
		} else {
			throw new MyAsyncNotFoundException("권한이 없습니다.");
		}
	}
}
