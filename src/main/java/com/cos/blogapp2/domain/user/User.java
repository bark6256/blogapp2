package com.cos.blogapp2.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor	// 초기화 하면서 값을 집어넣을때 사용
// @Setter	값을 변경할때 사용. DB의 값과 완벽하게 동기화가 되어야 하기 때문에 실무에서는 모델에 setter를 안쓴다. -> builder패턴
@Getter
@Entity	// 이게 있어야 테이블이 만들어진다
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 전략 만들기
	private int id;
	private String username;
	private String password;
	private String email;
}
