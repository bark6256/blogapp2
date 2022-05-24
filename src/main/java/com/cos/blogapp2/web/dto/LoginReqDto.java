package com.cos.blogapp2.web.dto;

import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginReqDto {
	private String username;
	private String password;
	
	public User toEntity() {
		User user = User.builder()
				.username(username)
				.password(password)
				.build();
		return user;
	}
}
