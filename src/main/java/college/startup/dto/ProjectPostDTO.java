package college.startup.dto;

import college.startup.domain.Project;
import college.startup.domain.User;
import lombok.*;

import java.util.Date;

@Builder
@ToString(exclude = {"project", "user"})
@EqualsAndHashCode
public class ProjectPostDTO {

    @Getter
    @Setter
    private Project project;
    private final User user;
    private final UserStats userStats;

    @Getter
    @Setter
    private Boolean isMyProject = null;

    public long getId() {
        return project.getId();
    }

    public String getContent() {
        return project.getContent();
    }

    public Date getCreatedAt() {
        return project.getCreatedAt();
    }

    public UserDTO getUser() {
        return UserDTO.builder()
                .user(user)
                .userStats(userStats)
                .isMyself(isMyProject)
                .build();
    }

}
