package example.springsecurity.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import example.springsecurity.domain.Studio;

@Getter
@AllArgsConstructor
public class StudioResponseDto {
    private String email;

    public static StudioResponseDto studioResponseDto(Studio studio) {
        return new StudioResponseDto(studio.getEmail());
    }
}
