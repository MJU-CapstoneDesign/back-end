package com.danram.server.service.post;

import com.danram.server.domain.post.Feed;
import com.danram.server.domain.post.Post;
import com.danram.server.repository.FeedRepository;
import com.danram.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final PostRepository postRepository;

    @Override
    public Feed findFeed(final Long partyId) {
        return feedRepository.findById(partyId).orElseThrow();
    }

    @Transactional
    @Override
    public void deleteFeed(final Long partyId) {
        final Feed feed = feedRepository.findById(partyId).orElseThrow();

        final List<Post> posts = feed.getPosts();

        if(posts.size() != 0) {
            for (Post post : posts) {
                log.info("called post delete method");
                postRepository.deleteByPostId(post.getPostId());
            }
        }

        feedRepository.delete(feed);
    }
}
