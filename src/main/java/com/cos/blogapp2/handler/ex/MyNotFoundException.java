package com.cos.blogapp2.handler.ex;

/**
 * 
 * @author 권한등이 없어 페이지 이동 실패. index페이지로 이동
 *
 */

public class MyNotFoundException extends RuntimeException{
	public MyNotFoundException(String msg) {
		super(msg);
	}
}
