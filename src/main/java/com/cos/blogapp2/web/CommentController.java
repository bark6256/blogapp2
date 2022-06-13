package com.cos.blogapp2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.service.CommentService;
import com.cos.blogapp2.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final CommentService commentService;
	private final HttpSession session;
	
	@DeleteMapping("/api/comment/{id}")
	public @ResponseBody CMRespDto<?> commentDelete(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		commentService.댓글삭제(id,principal);
		
		return new CMRespDto<>(1, "댓글 삭제 성공", null);
	}
}
