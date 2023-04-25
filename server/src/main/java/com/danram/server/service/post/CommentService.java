package com.danram.server.service.post;

import com.danram.server.domain.post.Comment;
import com.danram.server.domain.post.Post;
import com.danram.server.dto.request.CommentDto;
import org.springframework.stereotype.Service;


@Service
public interface CommentService {
    public Post addComment(CommentDto commentDto);
    public void deleteComment(Long id);
}
