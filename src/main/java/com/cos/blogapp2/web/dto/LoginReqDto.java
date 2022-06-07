package com.cos.blogapp2.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@NotBlank
	private String username;
	@Size(min = 4, max = 30)
	@NotBlank
	private String password;
	
	public User toEntity() {
		User user = User.builder()
				.username(username)
				.password(password)
				.build();
		return user;
	}
}
