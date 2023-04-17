package com.danram.server.domain.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "user_id", columnDefinition = "int")
    @ApiModelProperty(example = "고유 식별 ID")
    private Long userId;

    @Column(name = "name", columnDefinition = "varchar(12)")
    @ApiModelProperty(example = "닉네임")
    private String name;

    @Column(name = "profile", columnDefinition = "longtext")
    @ApiModelProperty(example = "사용지 이미지 url")
    private String profile;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    @ApiModelProperty(example = "사용자 권한 정보들")
    private List<Authority> authorities;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty(example = "access token & refresh token")
    private Tokens tokens;

    public boolean hasRole(String role) {
        for(int i = 0; i < authorities.size(); i++) {
            if(authorities.get(i).getRole().equals(role)) {
                return true;
            }
        }

        return false;
    }
}
