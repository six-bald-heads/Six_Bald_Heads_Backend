package com.sixbald.webide.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(unique = true, length = 100)
    private String email;
    @Column(unique = true, length = 100)
    private String nickname;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String profileImgUrl;

    @Builder
    public User(Long id, String password, Role role, String email, String nickname, String profileImgUrl) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = Role.USER;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateImage(String imageUrl){
        this.profileImgUrl = imageUrl;
    }

    public void updateNickname(String updateNickname) {
        this.nickname = updateNickname;
    }
    public void updatePassword(String password) {
        this.password = password;
    }
}
