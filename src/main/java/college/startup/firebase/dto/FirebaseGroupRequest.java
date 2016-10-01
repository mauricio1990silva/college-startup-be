package college.startup.firebase.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mauriciosilva on 9/21/16.
 */

@Data
@Builder
public class FirebaseGroupRequest {

    private String operation;
    private String notification_key_name;
    private String notification_key;
    private List<String> registration_ids = new ArrayList<>();

    public  enum GroupOperation {
        create, add, remove
    }
}
