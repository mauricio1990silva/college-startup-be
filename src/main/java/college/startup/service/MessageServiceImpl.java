package college.startup.service;

import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;
import college.startup.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mauriciosilva on 9/25/16.
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Override
    public List<MessageDTO> findMessages(User currentUser, Project project, PageParams pageParams) {
        final List<MessageDTO> messages = messageRepository.findMessages(currentUser, project, pageParams);
        messages.forEach(f -> {
            if (currentUser == null) return;
            f.setIsMyMessage(f.getId() == currentUser.getId());
        });

        return messages;
    }
}
