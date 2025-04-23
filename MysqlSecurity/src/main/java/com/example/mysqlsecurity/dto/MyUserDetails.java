package com.example.mysqlsecurity.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.mysqlsecurity.entity.MemberEntity;

public class MyUserDetails implements UserDetails{
	private static final long serialVersionUID = 1L;
	private MemberEntity member;
	
	//Constructor
	public MyUserDetails(MemberEntity member) {
		this.member = member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		//collection 객체 생성
		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		//collection 객체에 회원 권한 추가 => add() 메서드 사용
		collection.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return member.getRole();
			}
			
		});
		
		return collection;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

}
