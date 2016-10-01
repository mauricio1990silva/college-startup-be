package college.startup.firebase.rest;

import college.startup.config.FirebaseConfig;
import college.startup.domain.Project;
import college.startup.domain.User;
import college.startup.firebase.dto.*;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Notification;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mauriciosilva on 9/21/16.
 */

@Service
public class FirebaseGroupServiceImpl extends RestTemplate implements FirebaseGroupService {


    private List<ClientHttpRequestInterceptor> interceptors;
    private final FirebaseConfig firebaseConfig;
    private final Sender sender;
    private final String collapseKey = "collapseKey";
    private final boolean delayWhileIdle = true;
    private final boolean dryRun = true;
    private final int retries = 42;
    private final int ttl = 108;


    @Autowired
    public FirebaseGroupServiceImpl(FirebaseConfig firebaseConfig) {
        this.sender = new Sender(firebaseConfig.getServerKey());
        this.firebaseConfig = firebaseConfig;
        this.interceptors = new ArrayList<>();
        this.interceptors.add(new HeaderRequestInterceptor("Content-Type", MediaType.APPLICATION_JSON_VALUE));
        this.interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + firebaseConfig.getServerKey()));
        this.interceptors.add(new HeaderRequestInterceptor("project_id", firebaseConfig.getSenderId()));
        this.setInterceptors(interceptors);
    }


    @Override
    public FirebaseGroupResponse createGroup(User user, String projectName) {

        final List<String> registrationIds = new ArrayList<>();
        registrationIds.add(user.getFireBaseId());
        final StringBuilder name = new StringBuilder("appUser-");
        name.append(user.getUsername().substring(0, user.getUsername().indexOf("@")))
                .append("-" + projectName + "-" + UUID.randomUUID());

        final FirebaseGroupRequest firebaseGroupRequest = FirebaseGroupRequest.builder()
                .operation(FirebaseGroupRequest.GroupOperation.create.toString())
                .notification_key_name(name.toString())
                .registration_ids(registrationIds)
                .build();


        FirebaseGroupResponse firebaseGroupResponse = this.postForObject(this.firebaseConfig.getServerNotificationUrl(),
                firebaseGroupRequest, FirebaseGroupResponse.class);

        return firebaseGroupResponse;

    }

    @Override
    public FirebaseGroupResponse addUserToGroup(User founder, User anotherUser, Project project) {
        final List<String> registrationIds = new ArrayList<>();
        registrationIds.add(anotherUser.getFireBaseId());
        final FirebaseGroupRequest firebaseGroupRequest = FirebaseGroupRequest.builder()
                .operation(FirebaseGroupRequest.GroupOperation.add.toString())
                .notification_key_name("appUser" + founder.getUsername().indexOf("@"))
                .notification_key(project.getFireBaseId())
                .registration_ids(registrationIds)
                .build();

        return this.postForObject(this.firebaseConfig.getServerNotificationUrl(),
                firebaseGroupRequest, FirebaseGroupResponse.class);
    }

    @Override
    public FirebaseGroupResponse removeUserFromGroup(User founder, User anotherUser, Project project) {
        final List<String> registrationIds = new ArrayList<>();
        registrationIds.add(anotherUser.getFireBaseId());
        final FirebaseGroupRequest firebaseGroupRequest = FirebaseGroupRequest.builder()
                .operation(FirebaseGroupRequest.GroupOperation.remove.toString())
                .notification_key_name("appUser" + founder.getUsername().indexOf("@"))
                .notification_key(project.getFireBaseId())
                .registration_ids(registrationIds)
                .build();

        return this.postForObject(this.firebaseConfig.getServerNotificationUrl(),
                firebaseGroupRequest, FirebaseGroupResponse.class);
    }

    @Override
    public Result sendMessageToGroup(college.startup.domain.Message message) throws IOException {


        final Notification notification = new Notification.Builder("face")
                .title(message.getProject().getName())
                .body(message.getContent())
                .badge(2)
                .build();

        final Message fcmMessage = new Message.Builder()
                .collapseKey(collapseKey)
                .delayWhileIdle(delayWhileIdle)
                .notification(notification)
                .dryRun(dryRun)
                .timeToLive(ttl)
                .addData("content", message.getContent())
                .addData("sender", message.getUser().getName())
                .build();

        final Result result = sender.send(fcmMessage, message.getProject().getFireBaseId(), 0);
        return result;
    }

}
