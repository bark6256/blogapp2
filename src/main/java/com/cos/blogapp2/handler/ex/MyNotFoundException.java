package com.cos.blogapp2.handler.ex;

/**
 * 
 * @author 게시글을 못찾았을때 사용. 에러 페이지로 이동
 *
 */

public class MyNotFoundException extends RuntimeException{
	public MyNotFoundException(String msg) {
		super(msg);
	}
}
