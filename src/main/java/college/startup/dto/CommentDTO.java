package college.startup.dto;

import college.startup.domain.Comment;
import college.startup.domain.Project;
import college.startup.domain.User;
import lombok.*;

import java.util.Date;

@Builder
@ToString(exclude = {"comment", "user", "userStats"})
@EqualsAndHashCode
public class CommentDTO {

    private final Comment comment;
    private final User user;
    private final UserStats userStats;

    @Getter
    @Setter
    private Boolean isMyComment = null;

    public long getId() {
        return comment.getId();
    }

    public String getContent() {
        return comment.getContent();
    }

    public Date getCreatedAt() {
        return comment.getCreatedAt();
    }

    public UserDTO getUser() {
        return UserDTO.builder()
                .user(user)
                .userStats(userStats)
                .isMyself(isMyComment)
                .build();
    }

}
