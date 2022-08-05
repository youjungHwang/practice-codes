package example.springsecurity.web.controller;

import jdk.nashorn.internal.parser.Token;
import lombok.RequiredArgsConstructor;
import example.springsecurity.service.AuthService;
import example.springsecurity.web.dto.StudioRequestDto;
import example.springsecurity.web.dto.StudioResponseDto;
import example.springsecurity.web.dto.TokenDto;
import example.springsecurity.web.dto.TokenRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<StudioResponseDto> signUp(@RequestBody StudioRequestDto studioRequestDto) {
        return ResponseEntity.ok(authService.signUp(studioRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody StudioRequestDto studioRequestDto) {
        return ResponseEntity.ok(authService.login(studioRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(tokenRequestDto));
    }
}
