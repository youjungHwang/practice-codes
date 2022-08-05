package example.springsecurity.web.dto;

import example.springsecurity.domain.StudioRole;
import lombok.*;
import example.springsecurity.domain.Studio;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudioRequestDto {

    private String email;
    private String password;

    public Studio studio(PasswordEncoder passwordEncoder) {
        return Studio.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(StudioRole.ROLE_STUDIO)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
