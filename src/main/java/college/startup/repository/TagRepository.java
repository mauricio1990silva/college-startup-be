package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findOneByContent(String content);
}
