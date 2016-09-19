package college.startup.repository;


import college.startup.domain.Relationship;
import college.startup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Optional<Relationship> findOneByFollowerAndFollowed(User follower, User followed);
}
