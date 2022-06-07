package com.cos.blogapp2.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JoinReqDto {
	@NotBlank	// NotNull + NotEmpty
	private String username;
	@Size(min = 4, max = 30)
	@NotBlank
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
