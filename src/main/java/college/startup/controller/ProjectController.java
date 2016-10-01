package college.startup.controller;


import college.startup.domain.Project;
import college.startup.domain.Tag;
import college.startup.domain.User;
import college.startup.dto.ProjectParams;
import college.startup.firebase.rest.FirebaseGroupService;
import college.startup.repository.ProjectRepository;
import college.startup.repository.TagRepository;
import college.startup.service.ProjectService;
import college.startup.service.SecurityContextService;
import college.startup.service.exception.NotPermittedException;
import college.startup.util.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final SecurityContextService securityContextService;
    private final TagRepository tagRepository;
    private final FirebaseGroupService firebaseGroupService;

    @Autowired
    public ProjectController(ProjectRepository projectRepository,
                             ProjectService projectService,
                             TagRepository tagRepository,
                             FirebaseGroupService firebaseGroupService,
                             SecurityContextService securityContextService) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.firebaseGroupService = firebaseGroupService;
        this.securityContextService = securityContextService;
        this.tagRepository = tagRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Project create(@Valid @RequestBody ProjectParams projectParams) {
        final User user = securityContextService.currentUser();
        final Set<Tag> tags = RepositoryUtil.removeDuplicates(tagRepository, projectParams.getProjectTags().get());
        projectParams.setTags(tags);
        projectParams.setUser(user);
        projectParams.setFirebaseId(firebaseGroupService.createGroup(user, projectParams.getName().get())
                .getNotification_key());

        final Project project = projectParams.toProject();

        return projectService.create(project, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Project updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectParams projectParams) {
        final User user = securityContextService.currentUser();
        final Project project = projectRepository.findByUserAndId(user, id)
                .orElseThrow(UserNotOwnerException::new);

        return projectService.update(project, projectParams);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        projectService.delete(id);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "USER is not founder of the project")
    private class UserNotOwnerException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotPermittedException.class)
    public void handleNoPermission() {
    }
}
