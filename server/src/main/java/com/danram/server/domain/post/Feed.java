package com.danram.server.domain.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feed")
@ApiModel(value = "피드 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FeedInfo.class)
public class Feed {
    @Id
    @Column(name = "party_id", columnDefinition = "int")
    @ApiModelProperty(example = "party id")
    private Long partyId;

    @Id
    @Column(name = "post_id", columnDefinition = "int")
    @ApiModelProperty(example = "post id")
    private Long postId;
}
