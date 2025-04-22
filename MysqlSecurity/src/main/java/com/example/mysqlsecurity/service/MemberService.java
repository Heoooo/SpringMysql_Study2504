package com.example.mysqlsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mysqlsecurity.dto.MemberDTO;
import com.example.mysqlsecurity.entity.MemberEntity;
import com.example.mysqlsecurity.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	//실제 데이터베이스 입력
	public void create(MemberDTO memberDTO) {
		//전달된 memberDTO -> memberEntity로 변환
		MemberEntity member = new MemberEntity();
		
		//값 셋팅 -> setter, getter
		member.setUsername(memberDTO.getUsername());
		member.setPassword(memberDTO.getPassword());
		member.setRole("ROLE_USER");
		
		//데이터베이스 저장
		memberRepository.save(member);
		//save 단계에서 암호화를 하지 않고 평문 그대로 입력하면 => 에러 발생
		//코드 상에는 에러가 안보이지만 실제 입력하는 단계에서 에러 발생
		//따라서, 패스워드인코더를 빈으로 등록하고 사용자가 입력한 평문을 암호화하여 입력 => SecurityConfig.java
		//그리고, 회원가입 URL 경로에 대해서 권한 설정 => signup, memberProc => PermitAll()
		//회원가입 경로들에 대해서 permitAll() 처리를 해주지 않으면 => 에러는 없으나 회원가입이 되지 않는다.
	}
}	
