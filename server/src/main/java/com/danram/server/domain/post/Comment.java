package com.danram.server.domain.post;

import com.danram.server.dto.request.CommentDto;
import com.danram.server.util.JwtUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@ApiModel(value = "댓글 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "comment_id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(example = "고유 식별 ID")
    private Long commentId;

    @Column(name = "member_id", columnDefinition = "int")
    @ApiModelProperty(example = "멤버 아이디")
    private Long memberId;

    @Column(name = "content", columnDefinition = "varchar(60)")
    @ApiModelProperty(example = "댓글 내용")
    private String content;

    @Column(name = "parent_comment", columnDefinition = "int")
    @ApiModelProperty(example = "누구의 댓글인지")
    private Long parentComment;

    @Column(name = "created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성 날짜")
    private LocalDateTime createdAt;

    public static Comment of(CommentDto commentDto) {
        return Comment.builder()
                .memberId(JwtUtil.getMemberId().getId())
                .content(commentDto.getContent())
                .parentComment(commentDto.getParentComment())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
