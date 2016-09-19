package college.startup.dto;

import college.startup.domain.Tag;
import college.startup.domain.User;
import college.startup.domain.UserProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ToString
@EqualsAndHashCode
public final class UserProfileParams {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(UserProfileParams.class);

    private final String major;
    private final String year;
    @Setter
    private User user;
    @Setter
    @Getter
    private Set<Tag> tags;
    private final List<String> projectTags;

    public UserProfileParams(@JsonProperty("major") String major,
                             @JsonProperty("year") String year,
                             @JsonProperty("tags") List<String> projectTags) {
        this.major = major;
        this.year = year;
        this.projectTags = projectTags;
    }

    public Optional<String> getMajor() {
        return Optional.ofNullable(major);
    }

    public Optional<String> getYear() {
        return Optional.ofNullable(year);
    }

    public Optional<List<String>> getProjectTags() {
        return Optional.ofNullable(projectTags);
    }


    public UserProfile toUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setMajor(major);
        userProfile.setYear(year);
        userProfile.setUser(user);
        userProfile.setTags(tags);
        return userProfile;
    }



}
