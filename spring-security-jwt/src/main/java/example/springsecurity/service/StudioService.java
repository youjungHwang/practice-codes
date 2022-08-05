package example.springsecurity.service;

import lombok.RequiredArgsConstructor;
import example.springsecurity.domain.Studio;
import example.springsecurity.repository.StudioRepository;
import example.springsecurity.util.SecurityUtil;
import example.springsecurity.web.dto.StudioResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudioService {
    private final StudioRepository studioRepository;

    @Transactional(readOnly = true)
    public StudioResponseDto getStudioInfo(String email) {
        return studioRepository.findByEmail(email)
                .map(StudioResponseDto::studioResponseDto)
                .orElseThrow(() -> new RuntimeException("Studio 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 Studio 정보 가져오기
    @Transactional(readOnly = true)
    public StudioResponseDto getLoginStudioInfo() {
        return studioRepository.findById(SecurityUtil.getCurrentStudioId())
                .map(StudioResponseDto::studioResponseDto)
                .orElseThrow(() -> new RuntimeException("로그인 Studio 정보가 없습니다."));

    }

}
