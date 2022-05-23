package com.cos.blogapp2.web.dto;

import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
	
	public User toEntity() {
		User user = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
		return user;
	}
}
