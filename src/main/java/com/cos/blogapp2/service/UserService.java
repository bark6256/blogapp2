package com.cos.blogapp2.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp2.domain.user.User;
import com.cos.blogapp2.domain.user.UserRepository;
import com.cos.blogapp2.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.util.SHA;
import com.cos.blogapp2.web.dto.JoinReqDto;
import com.cos.blogapp2.web.dto.LoginReqDto;
import com.cos.blogapp2.web.dto.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final HttpSession session;
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 정보수정(UserUpdateReqDto dto, User principal) {
		// 기존 암호 확인
		String encPassword = SHA.encrypt(dto.getPassword());
		User user = userRepository.findByUsernameAndPassword(dto.getUsername(), encPassword);
		if ( !(user.getUsername().equals(principal.getUsername()) && user.getPassword().equals(principal.getPassword())) )
			throw new MyAsyncNotFoundException("현재 비밀번호가 맞지않습니다.");
		
		// DB 암호 변경
		String ecnNewPassword = SHA.encrypt(dto.getNewpassword());
		user.setPassword(ecnNewPassword);
		user.setEmail(dto.getEmail());
		
		// 새션 변경
		principal.setPassword(ecnNewPassword);
		principal.setEmail(dto.getEmail());
		session.setAttribute("principal", principal);
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public User 로그인(LoginReqDto dto) {
		String encPassword = SHA.encrypt(dto.getPassword());
		User principal = userRepository.findByUsernameAndPassword(dto.getUsername(), encPassword);
		
		return principal;
	}

	@Transactional(rollbackFor = MyNotFoundException.class)
	public void 회원가입(JoinReqDto dto) {
		String encPassword = SHA.encrypt(dto.getPassword());
		dto.setPassword(encPassword);
		User user = dto.toEntity();
		userRepository.save(user);
	}
}
