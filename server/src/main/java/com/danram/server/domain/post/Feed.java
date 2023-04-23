package com.danram.server.domain.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "feed")
@ApiModel(value = "피드 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feed {
    @Id
    @Column(name = "party_id", columnDefinition = "int")
    @ApiModelProperty(example = "party id")
    private Long partyId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "party_post",
            joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "party_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "post_id")})
    @ApiModelProperty(example = "게시물들")
    private List<Post> posts;

    public static Feed of(Long id) {
        return Feed.builder()
                .partyId(id)
                .build();
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }
}
