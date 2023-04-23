package com.danram.server.repository;

import com.danram.server.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public void deleteByPostId(Long id);
    public Optional<Post> findByPostId(Long id);
}
