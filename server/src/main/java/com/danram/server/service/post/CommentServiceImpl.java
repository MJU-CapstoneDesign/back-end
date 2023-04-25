package com.danram.server.service.post;

import com.danram.server.domain.post.Comment;
import com.danram.server.domain.post.Post;
import com.danram.server.dto.request.CommentDto;
import com.danram.server.repository.CommentRepository;
import com.danram.server.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public Post addComment(final CommentDto commentDto) {
        final Comment saveComment = commentRepository.save(Comment.of(commentDto));

        final Post post = postRepository.findByPostId(commentDto.getPostId()).orElseThrow();

        post.addComment(saveComment);

        return post;
    }

    @Override
    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
