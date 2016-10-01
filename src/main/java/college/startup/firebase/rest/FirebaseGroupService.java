package college.startup.firebase.rest;

import college.startup.domain.Message;
import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.firebase.dto.FirebaseGroupResponse;
import com.google.android.gcm.server.Result;

import java.io.IOException;

/**
 * Created by mauriciosilva on 9/21/16.
 */
public interface FirebaseGroupService {

    FirebaseGroupResponse createGroup(User user, String projectName);

    FirebaseGroupResponse addUserToGroup(User founder, User anotherUser, Project project);

    FirebaseGroupResponse removeUserFromGroup(User founder, User anotherUser, Project project);

    Result sendMessageToGroup(Message message) throws IOException;
}
