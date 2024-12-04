package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.sparta.msa_exam.auth.core.User;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String role;

    public static User toEntity(SignUpRequestDto requestDto) {
        return User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(requestDto.getRole())
                .build();
    }
}
