package com.cos.blogapp2.domain.board;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.cos.blogapp2.domain.comment.Comment;
import com.cos.blogapp2.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 전략 만들기
	private int id;
	@Column(nullable = false)
	private String title;
	@Lob	// 이게 있어야 저장 공간이 커진다(4GB)
	private String content;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	//양방향 매핑
	//mappedBy에는 FK의 주인의 변수이름을 추가한다.
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> comments;
}
