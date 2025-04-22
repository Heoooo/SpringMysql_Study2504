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
		

MySQL 명령 프롬프트에서 여러 가지 명령어 실습하기
1. 일단 설치 경로 체크 (Windows 기준)
	MySQL 설치 경로 확인 => 보통 => C:\Program Files\MySQL\MySQL Server 8.0/bin
 
2. mysql 서비스 실행하기 => Windows 기준 => net 명령어 사용
	서비스 창에 들어가서 해도 되고, 커맨드 모드에서 net 명령어 사용해서 하는 것도 가능
	서비스 시작 => net start mysql
	서비스 종료 => net stop mysql
	서버(서비스) 종료 => mysqladmin -u root -p shutdown
	
	여기서 서비스 이름이 틀리다고 나오면? => mysql80 이름으로 재입력
	그래도 안되면? 시스템 오류가 나오면? => 관리자 권한으로 명령 프롬프트 실행했는지 체크
	
	관리자 실행 여부 확인 => 창 이름쪽에 보면 "관리자"라고 나오면 관리자 권한으로 => 실행

3. 버전 확인
	mysql --version => 명령 프롬프트 직접 확인
	mysqladmin --version => 거의 비슷하게 출ㄺ
	SELECT VERSION(); => 쿼리로 확인
	
4. MySQL 서버에 접속하기
	mysql -u root -p

5. MySQL 서버 서비스 상태 확인 (Windows에서는 그냥 net 명령어 사용해서 체크)
	net start mysql80 => 서버가 시작되어 있다면 => "요청한 서비스가 이미 시작되었습니다." 출력
	net stop mysql180 => "MySQL80 서비스는 시작되지 않았습니다." 출력
	
	
MySQL 데이터베이스 연동 작업
1. 작업하기 전 사전에 체크해야 하는 것들
	-DB 연동을 하는 것이므로 build.gradle에서 주석 처리한 것들 => 해제
	-jpa, mysql-connector
	-해제 후, 프로젝트 새로고침
	-이것들을 해제하지 않고 진행하면 Entity 파일 만들 때 => @Entity => 임포트가 안되는 등의 에러 발생
	-application.properties 파일에서 MySQL DB 관련된 옵션들 => 작업
	-create database mysql_security 데이터베이스 미리 생성
	-MemberEntity => 테이블 생성은 => member_entity로 생성	

2. 필요한 파일들 작업 및 순서
	signup.html (회원가입 폼 페이지)
	Package
		controller
			SignupConroller.java
				-memberService() 필드
				-memberProc() 메서드
				-memberDTO 객체 => 앞에서는 memberCreateForm
		dto
			MemberDTO.java
		entity
			MemberEntity.java(엔티티 파일 => 데이터베이스 테이블 생성)
		service
			MemberService.java
		repository
			MemberRepository.java(인터페이스)

3. application.properties DB 설정
	-DATABASE 드라이버 연결 설정
		루트 패스워드 까먹지 않도록 주의
		연결 X => 돋보기에서 service 입력 후 mysql 서비스 "시작" 되었는지 체크
	-JPA 설정
		spring.jpa.hibernate.ddl-auto = update(또는 create, none)
		spring.jpa.hibernate.naming.physical-strategy = 
		(스프링부트에서 JPA를 사용할 때, 엔티티 속성 이름과 실제 테이블의 컬럼명 간의 매핑 방식을 설정하는데 사용)
	-커스텀 403 에러 페이지 URL 설정 (접근 권한 오류)
		spring.security.error.page = /error/403.html
	-세션 타임아웃 설정 (초 단위로 설정, 보통 기본값은 30분 정도로 설정)
		server.servlet.session.timeout = 1800