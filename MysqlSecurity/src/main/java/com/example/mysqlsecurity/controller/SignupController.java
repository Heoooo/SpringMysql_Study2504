package com.example.mysqlsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mysqlsecurity.dto.MemberDTO;
import com.example.mysqlsecurity.service.MemberService;

import jakarta.validation.Valid;

@Controller
public class SignupController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/signup")
	public String signupPage(MemberDTO memberDTO) {
		
		return "signup";
	}
	
	@PostMapping("/memberProc")
	public String memberProc(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {
		//회원가입 처리 메서드한테 DTO 전달
		memberService.create(memberDTO);
		
		model.addAttribute("alertMsg", "회원가입이 성공했습니다.");
		
		return "signup";
		//return "redirect:/login";
	}
}
