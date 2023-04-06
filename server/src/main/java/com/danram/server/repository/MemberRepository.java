package com.danram.server.repository;

import com.danram.server.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findMemberByName(String name);
    public Optional<Member> findMemberByUserId(Long id);
    @EntityGraph(attributePaths = "authorities") //쿼리가 수행 될 때 lazy 조회가 아니고 eager조회로 정보를 가져옴
    Optional<Member> findOneWithAuthoritiesByName(String username);
}