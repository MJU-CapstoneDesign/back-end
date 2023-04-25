package com.danram.server.domain.post;

import com.danram.server.dto.request.PostDto;
import com.danram.server.util.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
@ApiModel(value = "게시물 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "int")
    @ApiModelProperty(example = "게시물 아이디")
    private Long postId;

    @Column(name = "memberId", columnDefinition = "int")
    @ApiModelProperty(example = "member id")
    private Long memberId;

    @Column(name = "img", columnDefinition = "longtext")
    @ApiModelProperty(example = "게시물에 들어가는 이미지")
    private String img;

    @Column(name = "content", columnDefinition = "text")
    @ApiModelProperty(example = "본문 내용")
    private String content;

    @Column(name = "created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성 날짜")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_comment",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "comment_id", referencedColumnName = "comment_id")})
    @ApiModelProperty(example = "댓글들")
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public static Post of(PostDto postDto) {
        return Post.builder()
                .memberId(JwtUtil.getMemberId().getId())
                .content(postDto.getContent())
                .img(postDto.getImg())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
