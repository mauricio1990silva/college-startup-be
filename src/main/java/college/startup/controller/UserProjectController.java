package college.startup.controller;

import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectPostDTO;
import college.startup.repository.UserRepository;
import college.startup.service.ProjectService;
import college.startup.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProjectController {

    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final SecurityContextService securityContextService;

    @Autowired
    public UserProjectController(UserRepository userRepository, ProjectService projectService, SecurityContextService securityContextService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
        this.securityContextService = securityContextService;
    }

    @RequestMapping("/{userId:\\d+}/projects")
    public List<ProjectPostDTO> list(@PathVariable("userId") Long userId, PageParams pageParams) {
        final User user = userRepository.findOne(userId);
        return projectService.findByUser(user, pageParams);
    }

    @RequestMapping("/me/projects")
    public List<ProjectPostDTO> list(PageParams pageParams) {
        final User user = securityContextService.currentUser();
        return projectService.findByUser(user, pageParams);
    }

}
