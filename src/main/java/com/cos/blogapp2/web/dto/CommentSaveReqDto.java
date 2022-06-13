package com.cos.blogapp2.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.comment.Comment;
import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentSaveReqDto {
	@NotBlank
	@Size(min = 1, max = 255)
	private String content;
	
	public Comment toEntity(User principal, Board boardEntity) {
		Comment comment = Comment.builder()
				.content(content)
				.user(principal)
				.board(boardEntity)
				.build();
		return comment;
	}
}
