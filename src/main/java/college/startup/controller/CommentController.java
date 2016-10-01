package college.startup.controller;


import college.startup.domain.Comment;
import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.CommentParams;
import college.startup.repository.CommentRepository;
import college.startup.repository.ProjectRepository;
import college.startup.service.SecurityContextService;
import college.startup.service.exception.NotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/projects/{projectId}/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProjectRepository ProjectRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public CommentController(CommentRepository commentRepository,
                             ProjectRepository ProjectRepository,
                             SecurityContextService securityContextService) {
        this.commentRepository = commentRepository;
        this.ProjectRepository = ProjectRepository;
        this.securityContextService = securityContextService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Comment create(@PathVariable("projectId") Long projectId, @Valid @RequestBody CommentParams commentParams) {
        final User user = securityContextService.currentUser();
        final Project project = ProjectRepository.findOne(projectId);
        if (null == project) {
            throw new ProjectNotFoundException();
        }

        final Comment projectComment = new Comment(commentParams.getContent(), user, project);
        return commentRepository.save(projectComment);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        commentRepository.delete(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Project not found")
    private class ProjectNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotPermittedException.class)
    public void handleNoPermission() {
    }
}
