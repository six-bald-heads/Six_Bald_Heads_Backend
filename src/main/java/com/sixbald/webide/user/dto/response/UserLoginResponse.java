package com.sixbald.webide.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponse {
    @Schema(description = "유저 아이디", example = "1")
    private Long userId;
    @Schema(description = "유저 닉네임", example = "최강대머리")
    private String nickname;
    // TODO UserEntity role 수정 후 반영..
    @Schema(description = "유저 역할", example = "ROLE_USER")
    private String role;
    @Schema(description = "accessToken 정보", example = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJkb3JpYW5Acm9rLnJvayIsIm5pY2tuYW1lIjoi7LWc7ZWY66Gd66GdIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTY5NDc4NDk2MSwiZXhwIjoxNjk0Nzg0OTcxfQ.cRFMYkBO8E77FxHbyro_m5nmeJkmpKnHATslvAtPRc0")
    private String accessToken;

    @Builder
    public UserLoginResponse(Long userId, String nickname, String role, String accessToken) {
        this.userId = userId;
        this.nickname = nickname;
        this.role = "ROLE_USER";
        this.accessToken = accessToken;
    }
}
