package college.startup.service;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;

import java.util.List;

public interface MessageService {

    List<MessageDTO> findMessages (User user, Project project, PageParams pageParams);
}
