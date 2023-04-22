package com.danram.server.domain.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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

    @Column(name = "created_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "생성 날")
    private LocalDateTime createdAt;
}
