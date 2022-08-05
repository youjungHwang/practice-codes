package example.springsecurity.service;


import lombok.RequiredArgsConstructor;
import example.springsecurity.domain.CustomStudioDetails;
import example.springsecurity.domain.Studio;
import example.springsecurity.repository.StudioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudioRepository studioRepository;

    // 로그인 시 authenticate 메소드를 수행할때 DB에서 유저 정보를 조회해오는 loadUserByUsername 메소드가 실행
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Studio studio = studioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을/를 데이터베이스에서 찾을 수 없습니다."));

        // DB 에 Studio 값이 존재한다면 studioDetails 객체로 만들어서 리턴
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(studio.getRole().toString());

        List<GrantedAuthority> target = new ArrayList<>();
        target.add(grantedAuthority);

        CustomStudioDetails customStudioDetails = CustomStudioDetails.builder()
                .id(studio.getId())
                .email(studio.getEmail())
                .password(studio.getPassword())
                .role(target)
                .build();
        return  customStudioDetails;
    }
}
