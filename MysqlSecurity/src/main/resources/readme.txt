Spring Security + MySQL + Spring Boot => MySqlSecurity Project

	보통 데이터베이스 작업을 기준으로 공부하는 것도 전반부 + 후반부로 나뉜다고 생각하면 됨
	전반부 => 초기 설정, 컨트롤러, 템플릿 작업 등..
	후반부 => DTO, Entity, Service, Repository 등..
	
	1. 스프링 스타터 프로젝트
		-6개 정도 의존성 추가
		-Spring Web
		-Spring Security
		-Spring Data JPA
		-MySQL Driver
		-Lombok
		-Thymeleaf
		
	2. build.gradle DB 관련된 것들 일단 주석 처리 => 에러나니깐
		implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
		runtimeOnly 'com.mysql:mysql-connector-j'
		-이후에 데이터베이스 연결하고 본격적으로 DB 연동 시 다시 주석 풀고 프로젝트 새로고침
		-MySQL 잘 연결되는지 미리 테스트 => root 비밀번호도 미리 테스트
		
	3. 컨트롤러 및 템플릿 페이지 작업
		-필요한 것들 만들어가면서 테스트
		-기본적으로 스프링 시큐리티가 동작하는 것이므로 인증이 필요 => 임시 아이디(user), 비밀번호 사용
		-레이아웃 수정
			layout.html
			레이아웃에 삽입될 => fragment template page 작업
	
	4. SecurityConfig.java 파일에서 인증 작업
		-기본 에너테이션
			@Configuration
			@EnableWebSecurity
			@EnableMethodSecurity(prePostEnabled = true)
		-빈으로 등록하는 메서드
			protected SecurityFilterChain filterChain(HttpSecurity http)
			protected BCryptPasswordEncoder bCryptPasswordEncoder()
			//처음에 임시 비밀번호로 테스트할 때는 주석 처리 해놓고 사용하는게 필요
			//안그러면 에러 발생
		-http 주요 메서드
			authorizeHttpRequests()
				requestMatchers()
				anyRequest()
			formLogin()
				loginPage()
				loginProcessingUrl()
			csrf()
				disable()
		-람다 형식으로 사용 => 최신 버전으로 올라가면서 점점 이렇게 바뀌어 가는 추세라고 보면 됨