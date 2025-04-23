package com.example.mysqlsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mysqlsecurity.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{
	
	//MemberEntity findByUsername(String username);
	Optional<MemberEntity> findByUsername(String username);
	
}
