package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    Optional<Project> findByUserAndId(User user, Long id);
    Optional<Project> findOneById(Long id);

}
