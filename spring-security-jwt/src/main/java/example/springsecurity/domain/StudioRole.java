package example.springsecurity.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StudioRole {

    ROLE_STUDIO("ROLE_STUDIO"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;


}
