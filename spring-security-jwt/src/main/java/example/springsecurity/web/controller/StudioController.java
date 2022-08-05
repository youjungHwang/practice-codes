package example.springsecurity.web.controller;

import lombok.RequiredArgsConstructor;
import example.springsecurity.service.StudioService;
import example.springsecurity.web.dto.StudioResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studio")
public class StudioController {
    private final StudioService studioService;

    @GetMapping("/my")
    public ResponseEntity<StudioResponseDto> getLoginStudioInfo() {
        return ResponseEntity.ok(studioService.getLoginStudioInfo());
    }

    @GetMapping("/{email}")
    public ResponseEntity<StudioResponseDto> getStudioInfo(@PathVariable String email) {
        return ResponseEntity.ok(studioService.getStudioInfo(email));
    }

}
