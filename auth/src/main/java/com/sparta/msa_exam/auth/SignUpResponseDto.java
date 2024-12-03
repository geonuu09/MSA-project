package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String username;

    public static SignUpResponseDto fromEntity(User user){
        return SignUpResponseDto.builder()
                .username(user.getUsername())
                .build();
    }
}
