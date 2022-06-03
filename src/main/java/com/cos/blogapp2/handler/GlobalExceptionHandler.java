package com.cos.blogapp2.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cos.blogapp2.handler.ex.MyNotFoundException;

//controller - 1. 익셉션 핸들링, 2. @Controller의 역활을 한다.
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// fetch 요청
	@ExceptionHandler(value = MyNotFoundException.class)
	public String error1(MyNotFoundException e) {
		System.out.println("오류 발생");
		System.out.println(e.getMessage());
		
		return "error/errorPage";
	}
}
