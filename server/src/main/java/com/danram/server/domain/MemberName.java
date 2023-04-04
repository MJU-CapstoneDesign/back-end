package com.danram.server.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "fruits")
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
    private String name;

    @Column(name = "img", columnDefinition = "longtext")
    private String img;
}
