package com.cos.blogapp2.web.dto;

import javax.validation.constraints.NotBlank;

import com.cos.blogapp2.domain.board.Board;
import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BoardSaveReqDto {
	@NotBlank
	private String title;
	private String content;
	
	public Board toEntity(User principal) {
		Board board = Board.builder()
				.title(title)
				.content(content)
				.user(principal)
				.build();
		
		return board;
	}
}
