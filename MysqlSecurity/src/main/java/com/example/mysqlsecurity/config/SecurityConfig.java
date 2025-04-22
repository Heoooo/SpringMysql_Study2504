package com.example.mysqlsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //스프링 환경설정 파일임을 의미
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티 프레임워크의 통제를 받아라~ 라고 하는 애너테이션
@EnableMethodSecurity(prePostEnabled = true) //@PreAuthorize() 활성화 애너테이션
public class SecurityConfig {
	
	//빈으로 등록하는 메서드 => filterchain(), bCryptPasswordEncoder()
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//람다식 사용하여 Spring Security 인가 설정 시작
		//authorizeHttpRequests 메소드를 통해 권한 설정을 위한 객체 생성하고, 람다식을 사용하여 여러가지 설정 내용 정의
		//authorizeHttpRequests 메서드 역할
		//1. 이 메서드는 스프링 시큐리티 구성 메서드 내에서 사용되는 메서드 => HTTP 요청에 대한 인가 설정을 구성할 때 사용
		//2. 따라서, 이놈을 사용해서 다양한 인가 규칙 및 각각의 페이지 경로별로 다른 권한을 설정 => 람다식 사용 => requestMatchers() 사용
		http
			.authorizeHttpRequests((auth)->auth
					.requestMatchers("/").permitAll()
					.requestMatchers("/login", "/signup", "/memberProc").permitAll()
					.requestMatchers("/css/**", "/bootstrap/**").permitAll()
					.requestMatchers("/mypage/**").hasAnyRole("USER","ADMIN")
					.requestMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated()
					//기본적으로 기억해야 하는 것 => 접근 권한이 없는데 접근하려고 하면(설령 로그인했어도) => 403 Forbidden 에러 발생
					//403 에러용 템플릿 페이지 작성 => application.properties 파일에서 작업
					//이 때, 노란 밑줄(주의)이 나오면 툴에서 하라는대로 설정
					//templates > error 폴더 생성 후 => 403.html
			);
		
		http
			.formLogin((auth)->auth
					.loginPage("/login")
					.loginProcessingUrl("/loginProc").permitAll()
			);
		
		http
			.csrf((auth)->auth
					.disable()
			);
		
		
		return http.build();
	}
	
	
	//패스워드 인코더 등록
	@Bean
	protected BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
