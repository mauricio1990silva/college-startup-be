package college.startup.controller;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;
import college.startup.dto.RelatedUserDTO;
import college.startup.repository.ProjectRepository;
import college.startup.repository.UserRepository;
import college.startup.service.MessageService;
import college.startup.service.SecurityContextService;
import college.startup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}")
public class ProjectMessageController {

    private final ProjectRepository projectRepository;
    private final SecurityContextService securityContextService;
    private final MessageService messageService;

    @Autowired
    public ProjectMessageController(ProjectRepository projectRepository,
                                    MessageService messageService,
                                    SecurityContextService securityContextService) {
        this.projectRepository = projectRepository;
        this.messageService = messageService;
        this.securityContextService = securityContextService;
    }

    @RequestMapping("/messages")
    public List<MessageDTO> messages(@PathVariable("projectId") long projectId, PageParams pageParams) {
        final User user = securityContextService.currentUser();
        final Project project = projectRepository.findOne(projectId);

        return messageService.findMessages(user, project, pageParams);
    }
}
