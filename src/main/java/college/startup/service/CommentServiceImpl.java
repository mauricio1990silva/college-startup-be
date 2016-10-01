package college.startup.service;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.CommentDTO;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectPostDTO;
import college.startup.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    private final SecurityContextService securityContextService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(SecurityContextService securityContextService, CommentRepository commentRepository) {
        this.securityContextService = securityContextService;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDTO> findCommentsByProject(Project project, PageParams pageParams) {
        User currentUser = securityContextService.currentUser();
        final List<CommentDTO> comments = commentRepository.findComments(currentUser, project, pageParams);
        comments.forEach(c -> c.setIsMyComment(c.getUser().getId() == currentUser.getId()));
        return null;
    }
}
