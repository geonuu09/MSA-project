package com.sparta.msa_exam.auth;

import com.sparta.msa_exam.auth.core.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;

@Service // 서비스 계층으로 등록
public class AuthService {

    @Value("${spring.application.name}")
    private String issuer; // JWT 발행자 정보

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration; // JWT 만료 시간

    private final SecretKey secretKey; // JWT 서명 키
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화/복호화
    private final UserRepository userRepository; // 사용자 데이터 접근 객체

    // 생성자를 통해 의존성 주입
    public AuthService(@Value("${service.jwt.secret-key}") String secretKey,
                       PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * JWT 액세스 토큰 생성
     * @param username 사용자 ID
     * @param role 사용자 역할
     * @return 생성된 JWT 토큰 문자열
     */
    public String createAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // 사용자 ID를 클레임으로 설정
                .claim("role", role) // 사용자 역할을 클레임으로 추가
                .setIssuer(issuer) // JWT 발행자 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration)) // 만료 시간
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512) // 서명
                .compact();
    }

    /**
     * 회원가입 처리
     * @param requestDto 회원가입 요청 데이터
     * @return 저장된 사용자 정보
     */
    public User signUp(SignUpRequestDto requestDto) {
        // 사용자 중복 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 사용자가 존재합니다.");
        }

        // DTO를 엔티티로 변환 후 비밀번호 암호화
        User user = SignUpRequestDto.toEntity(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        // 사용자 저장
        return userRepository.save(user);
    }

    /**
     * 로그인 처리
     * @param requestDto 로그인 요청 데이터
     * @return 생성된 JWT 토큰
     */
    public String signIn(SignInRequestDto requestDto) {
        // 사용자 존재 여부 확인
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "사용자가 존재하지 않습니다."));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");
        }

        // JWT 토큰 생성
        return createAccessToken(user.getUsername(), user.getRole());
    }

    /**
     * 사용자 확인
     * @param userId 확인할 사용자 ID
     * @return 사용자 존재 여부 (true/false)
     */
    public boolean checkUser(Long userId) {
        return userRepository.existsById(userId); // 사용자 존재 여부 반환
    }
}
