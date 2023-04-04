package com.danram.server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "int")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", columnDefinition = "varchar(12)")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(20)")
    private String password;

    @Column(name = "nick_name", columnDefinition = "varchar(50)")
    private String nickname;

    @Column(name = "activated", columnDefinition = "bit")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
