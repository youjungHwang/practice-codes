package example.springsecurity.service;

import lombok.RequiredArgsConstructor;
import example.springsecurity.domain.RefreshToken;
import example.springsecurity.domain.Studio;
import example.springsecurity.jwt.TokenProvider;
import example.springsecurity.repository.RefreshTokenRepository;
import example.springsecurity.repository.StudioRepository;
import example.springsecurity.web.dto.StudioRequestDto;
import example.springsecurity.web.dto.StudioResponseDto;
import example.springsecurity.web.dto.TokenDto;
import example.springsecurity.web.dto.TokenRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //private final AuthenticationManager authenticationManager;
    private final StudioRepository studioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원가입
     */
    @Transactional
    public StudioResponseDto signUp(StudioRequestDto studioRequestDto) {
        if(studioRepository.existsByEmail(studioRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 스튜디오입니다.");
        }

        Studio studio = studioRequestDto.studio(passwordEncoder);
        return StudioResponseDto.studioResponseDto(studioRepository.save(studio));
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(StudioRequestDto studioRequestDto) {
        // 1. ID(email)/PW 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = studioRequestDto.toAuthentication();

        // 2. 실제 검증 로직(사용자 비밀번호 체크)
        // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행됨
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 발급
            return tokenDto;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 토큰 재발급
     */
    @Transactional
    public TokenDto refresh(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증 (validateToken() : 토큰 검증)
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        // 2. Access Token에서 Studio ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Studio ID를 기반으로 Refresh Token값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치 여부
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
