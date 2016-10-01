package college.startup.dto;

import college.startup.domain.Comment;
import college.startup.domain.Message;
import college.startup.domain.User;
import lombok.*;

import java.util.Date;

@Builder
@ToString(exclude = {"message", "user", "userStats"})
@EqualsAndHashCode
public class MessageDTO {

    private final Message message;
    private final User user;
    private final UserStats userStats;

    @Getter
    @Setter
    private Boolean isMyMessage = null;

    public long getId() {
        return message.getId();
    }

    public String getContent() {
        return message.getContent();
    }

    public Date getCreatedAt() {
        return message.getCreatedAt();
    }

    public UserDTO getUser() {
        return UserDTO.builder()
                .user(user)
                .userStats(userStats)
                .isMyself(isMyMessage)
                .build();
    }

}
