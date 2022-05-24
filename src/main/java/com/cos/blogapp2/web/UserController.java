package com.cos.blogapp2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.domain.user.UserRepository;
import com.cos.blogapp2.util.SHA;
import com.cos.blogapp2.web.dto.JoinReqDto;
import com.cos.blogapp2.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserRepository userRepository;
	private final HttpSession session;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(LoginReqDto dto) {
		
		String encPassword = SHA.encrypt(dto.getPassword());	//해쉬로 변경하기
		User principal = userRepository.findByUsernameAndPassword(dto.getUsername(), encPassword);
		
		if(principal == null) { // 로그인 실패
			return "redirect:/loginForm";
		} else {								// 로그인 성공
			// 세션 날라가는 조건 : 1.swssion.invalidate() 2. 브라우저 닫기
			session.setAttribute("principal", principal); // principal : 인증된 사용자.
			return "redirect:/";
		}
	}
	
	@PostMapping("/join")
	public String join(JoinReqDto dto) {
		
		String encPassword = SHA.encrypt(dto.getPassword());	//해쉬로 변경하기
		dto.setPassword(encPassword);
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
