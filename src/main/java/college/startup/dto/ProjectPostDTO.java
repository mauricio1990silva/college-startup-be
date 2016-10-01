package college.startup.dto;

import college.startup.domain.Project;
import college.startup.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@ToString(exclude = {"project", "user"})
@EqualsAndHashCode
public class ProjectPostDTO {

    @Getter
    @Setter
    private Project project;

    @Getter
    @JsonIgnore
    private final User user;
    private final UserStats userStats;
    private final List<String> tags;

    @Getter
    @Setter
    private Boolean isMyProject = null;

    @Getter
    @Setter
    private String myMembershipStatus = null;

    public List<String>  getTags(){
        return project.getTags().stream()
                .map( tag -> tag.getContent()).collect(Collectors.toList());
    }

    @JsonProperty("founder")
    public UserDTO getFounder() {
        return UserDTO.builder()
                .user(user)
                .userStats(userStats)
                .isMyself(isMyProject)
                .build();
    }

}
