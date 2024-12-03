package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.msa_exam.auth.core.User;

/**
 * 회원가입 요청 데이터 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String userId;   // 사용자 ID
    private String username; // 사용자 이름
    private String password; // 비밀번호
    private String role;     // 사용자 역할 (예: MEMBER, ADMIN)

    /**
     * DTO 데이터를 User 엔티티로 변환
     * @return User 엔티티 객체
     */
    public static User toEntity(SignUpRequestDto requestDto) {
        return User.builder()
                .userId(requestDto.getUserId())     // 사용자 ID
                .username(requestDto.getUsername()) // 사용자 이름
                .password(requestDto.getPassword()) // 비밀번호 (암호화 전)
                .role(requestDto.getRole())         // 사용자 역할
                .build();
    }
}
