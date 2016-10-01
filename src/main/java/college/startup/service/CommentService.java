package college.startup.service;



import college.startup.domain.Project;
import college.startup.dto.CommentDTO;
import college.startup.dto.PageParams;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findCommentsByProject(Project project, PageParams pageParams);

}
