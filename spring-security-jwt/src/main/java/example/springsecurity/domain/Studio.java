package example.springsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Studio {

    @Id
    @GeneratedValue
    @Column(name = "studio_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private StudioRole role;



}
