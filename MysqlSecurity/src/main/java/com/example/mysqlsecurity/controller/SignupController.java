package com.example.mysqlsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mysqlsecurity.dto.MemberDTO;
import com.example.mysqlsecurity.service.MemberService;

@Controller
public class SignupController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/signup")
	public String signupPage(MemberDTO memberDTO) {
		
		return "signup";
	}
	
	@PostMapping("/memberProc")
	public String memberProc(MemberDTO memberDTO) {
		//회원가입 처리 메서드한테 DTO 전달
		memberService.create(memberDTO);
		
		return "redirect:/login";
	}
}
