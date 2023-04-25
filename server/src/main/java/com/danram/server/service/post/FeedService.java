package com.danram.server.service.post;

import com.danram.server.domain.post.Feed;
import org.springframework.stereotype.Service;

@Service
public interface FeedService {
    public Feed findFeed(Long partyId);
    public void deleteFeed(Long partyId);
}
