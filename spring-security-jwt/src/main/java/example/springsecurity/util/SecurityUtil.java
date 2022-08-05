package example.springsecurity.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.awt.*;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    // JwtFilter 클래스의 doFilter 메소드에서 저장한 Security Context의 인증 정보에서 username을 리턴
    // 유저 정보에서 Studio ID 만 반환하는 메소드
    public static Long getCurrentStudioId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());

    }
}
