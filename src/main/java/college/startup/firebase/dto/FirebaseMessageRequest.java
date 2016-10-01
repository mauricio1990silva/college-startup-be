package college.startup.firebase.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Created by mauriciosilva on 9/21/16.
 */

@Data
@Builder
public class FirebaseMessageRequest {

    private String to;
    private final Map<String, String> data;
    //private Notification notification;

}
