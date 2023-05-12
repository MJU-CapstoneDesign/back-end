package com.danram.server.service.post;

import com.danram.server.domain.post.Feed;
import com.danram.server.domain.post.Post;
import com.danram.server.dto.request.PostDto;
import com.danram.server.repository.FeedRepository;
import com.danram.server.repository.PostRepository;
import com.danram.server.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final FeedRepository feedRepository;

    @Override
    public Post createPost(Long partyId, PostDto postDto) {
        Post post = Post.of(postDto);

        log.info("partyId: {}", partyId);

        final Feed feed = feedRepository.findById(partyId).get();

        feed.addPost(post);

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long partyId, Long postId) {
        //feed에서도 삭제
        final Post result = postRepository.findByPostId(postId).orElseThrow();

        final Feed feed = feedRepository.findById(partyId).orElseThrow();

        feed.removePost(result);

        postRepository.deleteByPostId(postId);
    }

    @Override
    public Post findPost(final Long postId) {
        return postRepository.findByPostId(postId).orElseThrow();
    }
}
