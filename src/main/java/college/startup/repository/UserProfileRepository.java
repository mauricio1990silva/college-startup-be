package college.startup.repository;


import college.startup.domain.User;
import college.startup.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileRepositoryCustom {

    Optional<UserProfile> findByUser(User user);
}
