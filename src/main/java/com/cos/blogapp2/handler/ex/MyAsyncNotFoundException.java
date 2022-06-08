package com.cos.blogapp2.handler.ex;

/**
 * 
 * @author 용세 2021.09.24
 * 1. fetch 요청에서 실패시
 *
 */

public class MyAsyncNotFoundException extends RuntimeException{
	public MyAsyncNotFoundException(String msg) {
		super(msg);
	}
}
