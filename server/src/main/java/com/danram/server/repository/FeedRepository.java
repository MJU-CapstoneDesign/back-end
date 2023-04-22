package com.danram.server.repository;

import com.danram.server.domain.post.Feed;
import com.danram.server.domain.post.FeedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, FeedInfo> {
}
