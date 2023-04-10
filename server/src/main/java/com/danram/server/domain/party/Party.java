package com.danram.server.domain.party;

import com.danram.server.domain.member.Member;
import com.danram.server.dto.request.PartyInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "party")
@ApiModel(value = "파티 테이블")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Party {
    @Id
    @Column(name = "party_id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(example = "고유 식별 ID")
    private Long partyId;

    @Column(name = "owner_id", columnDefinition = "int")
    @ApiModelProperty(example = "방장 id")
    private Long ownerId;

    @Column(name = "group_type", columnDefinition = "varchar(12)")
    @ApiModelProperty(example = "운동")
    private String groupType;

    @Column(name = "group_name", columnDefinition = "varchar(20)")
    @ApiModelProperty(example = "모임 이름")
    private String groupName;

    @Column(name = "party_img", columnDefinition = "text")
    @ApiModelProperty(example = "파티 사진")
    private String partyImg;

    @Column(name = "description", columnDefinition = "text")
    @ApiModelProperty(example = "상세 글")
    private String description;

    @Column(name = "location", columnDefinition = "varchar(50)")
    @ApiModelProperty(example = "용인시 기흥구 구갈동")
    private String location;

    @Column(name = "max", columnDefinition = "int")
    @ApiModelProperty(example = "최대 인원")
    private Long max;

    @Column(name = "start_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "모임 시작일")
    private LocalDateTime startAt;

    @Column(name = "end_at", columnDefinition = "datetime")
    @ApiModelProperty(example = "모집 종료일")
    private LocalDateTime endAt;

    @Column(name = "alarm_frequency", columnDefinition = "varchar(15)")
    @ApiModelProperty(example = "알람 횟수")
    private String alarmFrequency;

    @Column(name = "alarm_time", columnDefinition = "time")
    @ApiModelProperty(example = "알람 시간")
    private String alarmTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "party_members",
            joinColumns = {@JoinColumn(name = "party_id", referencedColumnName = "party_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    @ApiModelProperty(example = "파티원들")
    private List<Member> members;

    public static Party of(PartyInfoDto partyInfoDto, Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startAt = LocalDateTime.parse(partyInfoDto.getStartAt(), formatter);
        LocalDateTime endAt = LocalDateTime.parse(partyInfoDto.getStartAt(), formatter);

        return Party.builder()
                .ownerId(id)
                .groupType(partyInfoDto.getGroupType())
                .groupName(partyInfoDto.getGroupName())
                .partyImg(partyInfoDto.getPartyImg())
                .description(partyInfoDto.getDescription())
                .location(partyInfoDto.getLocation())
                .max(partyInfoDto.getMax())
                .startAt(startAt)
                .endAt(endAt)
                .alarmFrequency(partyInfoDto.getAlarmFrequency())
                .alarmTime(partyInfoDto.getAlarmTime())
                .build();
    }
}
