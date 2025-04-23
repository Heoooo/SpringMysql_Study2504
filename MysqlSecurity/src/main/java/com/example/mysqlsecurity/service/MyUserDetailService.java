package com.example.mysqlsecurity.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.mysqlsecurity.entity.MemberEntity;
import com.example.mysqlsecurity.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyUserDetailService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	//[2]번 방식
	//@Autowired
	//private MemberRepository memberRepository
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//사용자 아이디 검색
		Optional<MemberEntity> member = memberRepository.findByUsername(username);
		
		//사용자가 없다면 예외처리
		if (member.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 DB에서 검색할 수 없습니다.");
		}
		
		//Optional<MemberEntity> 타입을 MemberEntity 타입으로 변환
		MemberEntity memberEntity = member.get();
		
		//MyUserDetails 클래스로 MemberEntity 객체를 전달 => 권한을 추가 => 이때, 여기에서 해도 되나 MyUserDetails로 넘겨서 처리 => 분리 구현
			
		return new MyUserDetails(memberEntity);
	}

}
