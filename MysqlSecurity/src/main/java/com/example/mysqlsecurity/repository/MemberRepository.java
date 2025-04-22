package com.example.mysqlsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mysqlsecurity.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>{

}
