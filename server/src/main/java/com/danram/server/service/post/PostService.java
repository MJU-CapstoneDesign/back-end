package com.danram.server.service.post;

import com.danram.server.domain.post.Post;
import com.danram.server.dto.request.PostDto;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    public Post createPost(Long partyId, PostDto postDto);
    public void deletePost(Long partyId, Long postId);
    public Post findPost(Long partyId);
}
