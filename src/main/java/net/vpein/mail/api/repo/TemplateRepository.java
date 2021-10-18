package net.vpein.mail.api.repo;

import net.vpein.mail.api.entity.RksTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository  extends JpaRepository<RksTemplate, Long> {
    Optional<RksTemplate> findById(Long id);

}