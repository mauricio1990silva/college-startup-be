package college.startup.repository;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectPostDTO;

import java.util.List;

interface ProjectRepositoryCustom {

    List<ProjectPostDTO> findAsFeed(User user, PageParams pageParams);

    List<ProjectPostDTO> findAsPublicFeed(User user, PageParams pageParams);

    List<Project> findByUser(User user, PageParams pageParams);
}
