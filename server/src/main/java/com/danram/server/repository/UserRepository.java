package com.danram.server.repository;

import com.danram.server.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") //쿼리가 수행 될 때 lazy 조회가 아니고 eager조회로 정보를 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
