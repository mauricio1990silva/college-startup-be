package college.startup.dto;

import college.startup.domain.User;
import college.startup.domain.UserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@ToString(exclude = {"user"})
@EqualsAndHashCode
public class UserDTO {

    private final User user;

    @Getter
    private final UserStats userStats;

    @Setter
    private List<String> tags;

    @Getter
    @Setter
    private Boolean isMyself = null;

    public long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getUsername();
    }

    public String getName() {
        return user.getName();
    }

    public List<String>  getTags(){
        return user.getUserProfile().getTags().stream()
                .map( tag -> tag.getContent()).collect(Collectors.toList());
    }

    @JsonProperty("profile")
    public UserProfile getUserProfile(){
        return user.getUserProfile();
    }
}
