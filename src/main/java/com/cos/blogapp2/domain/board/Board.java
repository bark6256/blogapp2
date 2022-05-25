package com.cos.blogapp2.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 전략 만들기
	private int id;
	@Column(nullable = false)
	private String title;
	@Lob	// 이게 있어야 저장 공간이 커진다(4GB)
	private String content;
	
	@ManyToOne
	private User user;
}
