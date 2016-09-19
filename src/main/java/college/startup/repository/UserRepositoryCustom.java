package college.startup.repository;


import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.RelatedUserDTO;
import college.startup.dto.UserDTO;

import java.util.List;
import java.util.Optional;

interface UserRepositoryCustom {

    List<RelatedUserDTO> findFollowings(User user, User currentUser, PageParams pageParams);

    List<RelatedUserDTO> findFollowers(User user, User currentUser, PageParams pageParams);

    Optional<UserDTO> findOne(Long userId, User currentUser);
}
