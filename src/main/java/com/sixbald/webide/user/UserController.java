package com.sixbald.webide.user;

import com.sixbald.webide.common.Response;
import com.sixbald.webide.config.auth.LoginUser;
import com.sixbald.webide.domain.User;
import com.sixbald.webide.user.dto.request.*;
import com.sixbald.webide.user.dto.response.UserDTO;
import com.sixbald.webide.user.dto.response.UserLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;


@Slf4j
@RestController
@Tag(name = "유저 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @ApiResponses(value = {
            @ApiResponse(description = "회원가입 성공", responseCode = "200"),
            @ApiResponse(description = "회원가입 실패", responseCode = "400")
    })
    @PostMapping("/signup")
    public Response<Void> signup(@RequestBody @Valid UserSignupRequest dto) {
        userService.signup(dto);
        return Response.success("회원가입 성공");
    }

    @ApiResponses(value = {
            @ApiResponse(description = "로그인 성공", responseCode = "200"),
            @ApiResponse(description = "로그인 실패", responseCode = "400")
    })
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        return Response.success("로그인에 성공했습니다.", userService.login(request));
    }

    @PostMapping("/reissue")
    public Response<UserLoginResponse> reissue(@RequestBody RefreshTokenRequest request) {
        return Response.success("토큰 발급에 성공했습니다.", userService.reissue(request.getRefreshToken()));
    }

    @ApiResponses(value = {
            @ApiResponse(description = "로그아웃 성공", responseCode = "200")
    })
    @PostMapping("/logout")
    public Response<Void> logout() {
        return Response.success("로그아웃 되었습니다.");
    }
    // 프로필 조회

    @GetMapping("/profile")
    @Operation(summary = "프로필 조회")
    public Response<UserDTO> findUserInfo(
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        Long userId = loginUser.getUser().getId();
        UserDTO data = userService.getUserInfo(userId);
        return Response.success("회원 조회에 성공하셨습니다", data);
    }
    //프로필 이미지 수정

    @PutMapping(
            value = "/profile/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "프로필 이미지 수정")
    public Response<Void> updateImage(
            @NotNull
            @RequestPart MultipartFile imageUrl,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        userService.updateUserProfileImage(loginUser.getUser().getId(), imageUrl);
        return Response.success("프로필 이미지 수정 성공");
    }
    // 프로필 닉네임 수정

    @PutMapping("/profile/nickname")
    @Operation(summary = "프로필 닉네임 수정")
    public Response<Void> updateNickname(
            @RequestBody RequestNickname requestNickname,
            @AuthenticationPrincipal LoginUser loginUser
    ) {
        return userService.updateNickname(loginUser.getUser().getId(), requestNickname);
    }

    @PostMapping("/nickcheck")
    public Response<Void> nicknameCheck(@RequestBody NicknameRequest request) {
        return userService.nicknameCheck(request);
    }

    @PutMapping("/password")
    public Response<Void> passwordEdit(@RequestBody @Valid PasswordRequest request, @AuthenticationPrincipal LoginUser loginUser) {
        return userService.passwordEdit(request, loginUser);
    }

    @PostMapping("/sendmail")
    public Response<Void> sendMail(@RequestBody SendEmailRequest request) {
        return userService.sendMail(request);
    }

    @PostMapping("/emailcheck")
    public Response<Void> emailCheck(@RequestBody EmailCheckRequest request) {
        return userService.emailCheck(request);
    }

    @PostMapping("/findpassword")
    public Response<Void> findPassword(@RequestBody FindPasswordRequest request) {
        return userService.findPassword(request);
    }

    @DeleteMapping("/profile")
    public Response<Void> deleteUser(@AuthenticationPrincipal LoginUser loginUser){
        userService.deleteUser(loginUser);
        return Response.success("회원 탈퇴 완료");
    }
}
