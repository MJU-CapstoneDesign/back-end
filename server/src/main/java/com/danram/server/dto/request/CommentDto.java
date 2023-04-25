package com.danram.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long postId;
    private String content;
    private Long parentComment;
}
