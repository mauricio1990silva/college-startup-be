package college.startup.service;


import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.RelatedUserDTO;
import college.startup.dto.UserDTO;
import college.startup.dto.UserParams;

import java.util.List;
import java.util.Optional;

public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService {

    User update(User user, UserParams params);

    List<RelatedUserDTO> findFollowings(User user, PageParams pageParams);

    List<RelatedUserDTO> findFollowers(User user, PageParams pageParams);

    Optional<UserDTO> findOne(Long id);

    Optional<UserDTO> findMe();
}
