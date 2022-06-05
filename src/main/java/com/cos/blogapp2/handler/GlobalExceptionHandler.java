package com.cos.blogapp2.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp2.handler.ex.MyNotFoundException;
import com.cos.blogapp2.util.Script;

//controller - 1. 익셉션 핸들링, 2. @Controller의 역활을 한다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// fetch 요청, 뒤로가기를 사용한다
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류 발생 : " + e.getMessage());
		
		return Script.href("/", e.getMessage());
	}
}
