package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.CommentDTO;
import college.startup.dto.PageParams;

import java.util.List;

/**
 * Created by mauriciosilva on 9/20/16.
 */
interface CommentRepositoryCustom {

    List<CommentDTO> findComments (User user, Project project, PageParams pageParams);
}
