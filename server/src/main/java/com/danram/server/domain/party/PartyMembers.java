package com.danram.server.domain.party;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "party_members")
@ApiModel(value = "파티에 참여한 멤버 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PartyInfo.class)
public class PartyMembers {
    @Id
    @Column(name = "party_id", columnDefinition = "int")
    @ApiModelProperty(example = "party id")
    private Long partyId;

    @Id
    @Column(name = "user_id", columnDefinition = "int")
    @ApiModelProperty(example = "member id")
    private Long userId;
}
