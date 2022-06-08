package com.cos.blogapp2.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.domain.user.UserRepository;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.util.SHA;
import com.cos.blogapp2.util.Script;
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
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			String field = bindingResult.getFieldError().getField();
			String message = bindingResult.getFieldError().getDefaultMessage();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " + error.getField());
				System.out.println("메시지 : " + error.getDefaultMessage());
			}
			throw new MyNotFoundException(message);
		}
		String encPassword = SHA.encrypt(dto.getPassword()); // 해쉬로 변경하기
		User principal = userRepository.findByUsernameAndPassword(dto.getUsername(), encPassword);

		if (principal == null) { // 로그인 실패
			return Script.back("아이디 또는 비밀번호가 맞지않습니다.");
		} else { // 로그인 성공
			// 세션 날라가는 조건 : 1.swssion.invalidate() 2. 브라우저 닫기
			session.setAttribute("principal", principal); // principal : 인증된 사용자.
			return Script.href("/", "로그인 성공");
		}
	}

	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			String field = bindingResult.getFieldError().getField();
			String message = bindingResult.getFieldError().getDefaultMessage();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " + error.getField());
				System.out.println("메시지 : " + error.getDefaultMessage());
			}
			switch (field) {
			case "username":
				return Script.back("아이디는 " + message);
			case "password":
				return Script.back("비밀번호는 " + message);
			default:
			}
			return Script.back(field + "는 " + message);
		}
		String encPassword = SHA.encrypt(dto.getPassword()); // 해쉬로 변경하기
		dto.setPassword(encPassword);
		User user = dto.toEntity();
		userRepository.save(user);

		return Script.href("/loginForm", "회원가입 성공");
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
