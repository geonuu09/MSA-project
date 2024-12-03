package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String userId;
    private String username;
    private String password;
    private String role;

    public static User toEntity(SignUpRequestDto requestDto){
        return User.builder()
                .userId(requestDto.getUserId())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(requestDto.getRole())
                .build();
    }
}
