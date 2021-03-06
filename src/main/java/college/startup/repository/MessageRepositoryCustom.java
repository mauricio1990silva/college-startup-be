package college.startup.repository;


import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;

import java.util.List;

interface MessageRepositoryCustom {

    List<MessageDTO> findMessages(User currentUser, Project project, PageParams pageParams);

}
