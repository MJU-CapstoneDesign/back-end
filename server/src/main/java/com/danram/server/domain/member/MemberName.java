package com.danram.server.domain.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "fruits")
@ApiModel(value = "멤버 이름으로 쓸 이름들이 있는 테이블")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberName {
    @Id
    @Column(name = "id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(12)")
    @ApiModelProperty(example = "과일 이름")
    private String name;

    @Column(name = "img", columnDefinition = "longtext")
    @ApiModelProperty(example = "이미지 URL")
    private String img;
}
