package example.springsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "rt_key" )
    private String key;

    @Column(name = "rt_value")
    private String value;           // Refresh Token String

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}
