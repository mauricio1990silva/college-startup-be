package college.startup.controller;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.CommentDTO;
import college.startup.dto.PageParams;
import college.startup.dto.RelatedUserDTO;
import college.startup.repository.CommentRepository;
import college.startup.repository.ProjectRepository;
import college.startup.repository.UserRepository;
import college.startup.service.CommentService;
import college.startup.service.ProjectService;
import college.startup.service.SecurityContextService;
import college.startup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/comments")
public class ProjectCommentController {

    private final CommentService commentService;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectCommentController(CommentService commentService,
                                    ProjectRepository projectRepository) {
        this.commentService = commentService;
        this.projectRepository = projectRepository;
    }

    @RequestMapping
    public List<CommentDTO> findComments(@PathVariable("projectId") long projectId, PageParams pageParams) {
        final Project project = projectRepository.findOne(projectId);
        return commentService.findCommentsByProject(project, pageParams);
    }
}
