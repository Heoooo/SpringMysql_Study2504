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
		
		
MySQL + Spring Security 연동을 통한 데이터베이스 로그인 인증
1. 선수 요건
	-기본적으로 회원가입 폼(Form) 페이지를 통해서 아이디, 패스워드가 DB에 암호화되어 입력이 가능
	-로그인 템플릿 페이지 구현
	
2. 로그인 흐름
	-로그인 페이지를 통해서 아이디, 패스워드를 POST 방식으로 전송
	-일단 구현해줘야 하는 것들을 구현해놓지 않았다면 => 당연히 로그인 인증은 X
	
3. DB 기반 로그인 인증을 구현하기 위해 필요한 파일들
	-일단 2개 파일 생성 + 1개 파일 추가
		>MyUserDetailsService.java -> implements 구현 메서드
		>MyUserDetails.java
		>MemberRepository.java => findByUsername() 메서드 추가
	-UserDetailsService 구현
		>프레임워크에서 제공하는 UserDetailsService를 상속받아 => MyUserDetailsService 구현(implements)
		>서비스단에서 MyUserDetailsService.java 파일 생성
		>기본적으로 구현해줘야 하는 메서드(오버라이드)가 필요 => loadUserByUsername(String username) => UserDetails 타입 반환
		>loadUserByUsername() 메서드에서 하는 일 => DB에서 아이디 가져오고 => 가져온거 검증
		>DB 검색을 위해서 MemberRepository 하고 연결이 필요 => 연결 방법은 다양 => @Autowired 필드 주입 방식을 사용
		>간단하게 할 때는 필드 주입 방식을 사용하나, 실전에서는 생성자 주입 방식을 권장 => 아니면 롬복 애너테이션 등을 사용하는 것도 권장
	-loadUserByUsername()
		>인자로 String username을 받음
		>따라서, 이 username을 가지고 데이터베이스에 가서 검색하고 가져와서 검증
		>이때 검색을 위해서 MemberRepository에 findByUsername() 메서드 추가 => 이때 반환값은 => MemberEntity
		>데이터베이스에 아이디가 있다면 => return new MyUserDetails(member);
	-UserDetails 구현
		>일단 얘같은 경우는 Entity에서 UserDetails를 상속 받아 구현하는 것도 가능하나 개별 분리하여 구현
		>이때 MyUserDetails 클래스는 DTO에 해당하므로 => DTO 패키지에 MyUserDetails.java 생성
		>MyUserDetails 에서는 MemberEntity 회원 객체를 전달 받아서 회원이 가지고 있는 권한, 아이디, 패스워드 등을 꺼내서 리턴
		>시큐리티 프레임워크에서 제공하는 UserDetails를 상속받아 => MyUserDetails 구현(implements)
		>역시 위에서 한 것처럼 기본적으로 구현해줘야 하는 메서드가 필요 => 이때, 생성자를 사용해서 member 객체를 연결
			getAuthorities() => 사용자의 특정 권한을 반환 (코딩 약간 복잡) => member.getRole();
			getPassword() => 회원의 패스워드 값 반환 => member.getPassword();
			getUsername() => 회원의 사용자이름(아이디) 값 반환 => member.getUsername();
		>위 3개가 가장 기본적인 구현 메서드이고 기타 다른 것들도 있으나 DB에 필드 값을 만들어 놓지 않았다면 당장 사용할 일이 X
		>사용자 아이디 만료, 잠금 여부, 사용 가능 등을 체크하는 메서드 구현 등도 가능 => 기본값은 false 설정
		>아무튼 DB에 해당 값을 넣지 않았다면 리턴 값을 모두 => true 설정
		>이러한 메서드를 구현하여 적용하려면 DB에 필드 값도 만들어 놓아야하고, 기타 구현해야 하는 것들도 생각해야 하므로 많은 시간이 필요
		
4. 로그인 테스트
	-일단, 모든 것을 다 구현하고 하는 것이 아니므로 권한(role) 필드의 값을 수동으로 바꿔가면서 테스트
	-DB에 회원의 값이 ROLE_USER로 들어있는 상태에서 테스트해보고 => 다시 ROLE_ADMIN으로 변경하여 테스트 해보고...
	-이때, 페이지별 사용자(아이디) 접근 권한은 => SecurityConfig.java 파일에서 작업
	-테스트할 때 초보자가 자주 실수하는 것들
		>@Service 애너테이션을 안붙이고 서버 리스트하여 실행
		>추가 메서드 구현이 필요한 경우 => true 설정 안하고 실행 => 기본값은 false라서 변경이 필요
		>기본적으로 위와 같이 로그인 처리를 모두 구현했다면 => 서버 리스타트 시 => 임시 비밀번호 생성이 안되고 서버가 실행
	-http://localhost:8080/admin 페이지에 접속하면
		>일단 로그인 하지 않고 접근한다면 => 로그인 페이지로 강제 이동
		>로그인 후 접근한다면 => 권한이 ROLE_USER 인 경우 => 권한이 없으므로 403 접근 권한 오류 발생
		>로그인 후 접근한다면 => 권한이 ROLE_ADMIN 인 경우 => 에러 없이 잘 접속
		
5. 로그아웃 처리
	-기본적으로 시큐리티 프레임워크는 편리성을 위해서 /logout 이라는 특별한 요청에 대해서는 자동 로그아웃 기능이 실행
	-따라서, 특별히 로그아웃 구현하지 않아도 일단 로그아웃은 실행
	-만약에 디테일하게 로그아웃 후 이동 페이지 지정, 로그아웃 후 실행할 동작 등을 지정하고자 한다면 => 관련된 로그아웃 필터링 처리를 구현
	-자동 로그아웃 후 이동하는 페이지는 => 다시 /login 페이지로 이동
	

테스트 (체크사항)
1. 서버 리스타트 에러 없이 실행되는지 체크?
	-MySQL 서버가 시작이 안되어있다면? => 오류 발생
2. 서버 리스타트 할 때 임시 비밀번호 생성이 되는지 체크?
3. 회원 권한별로 가입했는지 체크?
	-superman => ROLE_USER
	-admin => ROLE_ADMIN
	-MemberService.java 파일에서 수동으로 바꾸거나 또는 커맨드 모드에서 쿼리로 직접 변경
	-UPDATE member_entity SET role ='ROLE_ADMIN' WhERE username='admin';
4. http://localhost:8080/admin 페이지로 접속되는지 체크?
	-로그인 전 => 로그인 페이지로 강제 이동
	-로그인 후 => 권한이 없다면 => 403 에러 페이지 출력 => 403 전용 에러 페이지를 이쁘게 만들어서 적용
	-로그인 후 => 권한이 있다면 => 해당 페이지 출력 => 부모 레이아웃 적용이 필요하면 적용
5. 로그아웃 테스트
	-자동 로그아웃이 되니깐 로그인 후 그냥 로그아웃 버튼 클릭 => /login 페이지로 이동
6. 기타
	-앞에서 공부한 내용들을 MysqlSecurity 프로젝트에 적용
	-회원가입 중복 체크(아이디)
		>MemberEntity.java => username 필드 위에 @Column(unique = true) 적용 => 이때, ddl-auto=create 로 설정하고 테스트
		>MemberService.java => 조건문으로 처리 => boolean existsByUsername() 메서드를 MemberRepository에 추가
		>이상은 백엔드 단에서 처리하는 것 => 물론, 프론트엔드 단에서도 할 수 있으나 바로 치고 물어볼 수도 있으므로 백엔드 단에서의 처리는 => 거의 필수
		>기타 정규식 사용하여 아이디 입력 시 다양한 입력 폼 제한을 적용
	-회원가입 DB 필드도 더 만들어 적용
	-@Pattern 사용하여 입력 폼에 다양한 정규표현식 적용
	-403 에러 페이지, admin 페이지 디자인도 이쁘게 만들어서 적용
	-로그인할 때 회원 권한을 리스트에 추가하는 것도 앞에서 공부한 방식으로 적용
	-로그인 후 회원 정보를 출력하는 것도 적용
	-페이지 내에서 admin 권한이 있어야 볼 수 있는 div 박스도 적용
	-페이징
	