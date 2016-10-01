package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.Membership;
import college.startup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long>, MembershipRepositoryCustom {
    Optional<Membership> findOneByUserAndProject(User user, Project project);

}
