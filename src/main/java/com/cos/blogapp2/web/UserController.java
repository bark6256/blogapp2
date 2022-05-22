package com.cos.blogapp2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.domain.user.UserRepository;
import com.cos.blogapp2.web.dto.JoinReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserRepository userRepository;
	
	@PostMapping("/join")
	public String join(JoinReqDto dto) {
		
		User user = dto.toEntity();
		userRepository.save(user);
		
		return "redirect:/loginForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/user/{id}")
	public String userInfo(@PathVariable int id) {
		return "user/updateForm";
	}
}
