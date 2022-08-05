package example.springsecurity.repository;

import example.springsecurity.domain.Studio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByEmail(String email);

    boolean existsByEmail(String email); // 이미 가입된 이메일인지 확인용
}
