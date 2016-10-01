package college.startup.controller;

import college.startup.domain.Membership;
import college.startup.domain.Message;
import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.dto.CommentParams;
import college.startup.dto.MessageParams;
import college.startup.firebase.rest.FirebaseGroupService;
import college.startup.repository.MembershipRepository;
import college.startup.repository.MessageRepository;
import college.startup.repository.ProjectRepository;
import college.startup.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by mauriciosilva on 9/24/16.
 */

@RestController
@RequestMapping("/api/messages")
public class MessageController {


    private final ProjectRepository projectRepository;
    private final MessageRepository messageRepository;
    private final SecurityContextService securityContextService;
    private final FirebaseGroupService firebaseGroupService;

    @Autowired
    public MessageController(ProjectRepository projectRepository,
                             MessageRepository messageRepository,
                             FirebaseGroupService firebaseGroupService,
                             SecurityContextService securityContextService) {
        this.projectRepository = projectRepository;
        this.messageRepository = messageRepository;
        this.securityContextService = securityContextService;
        this.firebaseGroupService = firebaseGroupService;
    }


    @RequestMapping(value = "/to/projects/{projectId}", method = RequestMethod.POST)
    public Message send(@PathVariable("projectId") Long projectId, @Valid @RequestBody MessageParams messageParams) {
        final Project project = projectRepository.findOne(projectId);
        final User currentUser = securityContextService.currentUser();

        final Message message = new Message(currentUser, project, messageParams.getContent());
        try {
            firebaseGroupService.sendMessageToGroup(message);
        } catch (IOException e) {
            e.printStackTrace();
           throw new MessageSendingError();
        }

        return messageRepository.save(message);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error sending message")
    private class MessageSendingError extends RuntimeException {
    }
}
