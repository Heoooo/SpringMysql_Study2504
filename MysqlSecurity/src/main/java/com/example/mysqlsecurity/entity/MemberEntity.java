package com.example.mysqlsecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
//초보자가 많이 경험하는 실수
//이 단계에서 임포트가 되지 않는다면?
//보통 에러 메시지 => Entity cannot be resolved to a type
//이 에러는 Spring Data JPA를 사용하여 엔티티 클래스를 정의할 때 자주 발생하는 문제 => 보통 JPA 의존성 누락
//또는 버전이 잘 맞지 않는 경우에도 발생
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //점찍고 자동으로 안나오면 필드 선언 후 다시 시도
	private int id;
	
	private String username;
	
	private String password;
	
	private String role;
	//권한을 저장할 필드를 필수적으로 생성
	//가장 기본적이고 필수적인 필드 3가지 =>username, password, role
}
