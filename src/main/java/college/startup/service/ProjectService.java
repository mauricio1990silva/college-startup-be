package college.startup.service;



import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectParams;
import college.startup.dto.ProjectPostDTO;

import java.util.List;

public interface ProjectService {

    void delete(Long id);

    Project update(Project project, ProjectParams projectParams);

    List<ProjectPostDTO> findAsFeed(PageParams pageParams);

    List<ProjectPostDTO> findAsPublicFeed(PageParams pageParams);

    List<ProjectPostDTO> findByUser(User user, PageParams pageParams);
}
