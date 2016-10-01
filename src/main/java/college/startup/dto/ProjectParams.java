package college.startup.dto;

import college.startup.domain.Project;
import college.startup.domain.Tag;
import college.startup.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.constraints.NotNull;
@ToString
@EqualsAndHashCode
public final class ProjectParams {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ProjectParams.class);
    @NotNull
    private final String name;
    @Size(min = 8, max = 100)
    private final String type;
    private final String content;
    private final int membersRequired;
    private final List<String> projectTags;

    @Getter
    @Setter
    private  String firebaseId;

    @Setter
    @Getter
    private Set<Tag> tags;

    @Setter
    private User user;

    public ProjectParams(@JsonProperty("name") String name,
                         @JsonProperty("type") String type,
                         @JsonProperty("content") String content,
                         @JsonProperty("membersRequired") int membersRequired,
                         @JsonProperty("tags") List<String> projectTags) {

        this.name = name;
        this.type = type;
        this.content = content;
        this.membersRequired = membersRequired;
        this.projectTags = projectTags;
        this.firebaseId = "";
    }


    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getType() {
        return Optional.ofNullable(type);
    }

    public Optional<String> getContent() {
        return Optional.ofNullable(content);
    }

    public Optional<Integer> getMembersRequired() {
        return Optional.ofNullable(membersRequired);
    }

    public Optional<List<String>> getProjectTags() {
        return Optional.ofNullable(projectTags);
    }

    public Optional<Set<Tag>> getTags() {
        return Optional.ofNullable(tags);
    }

    public Project toProject(){
        Project project = new Project();
        project.setName(name);
        project.setUser(user);
        project.setType(type);
        project.setContent(content);
        project.setMembersRequired(membersRequired);
        project.setTags(tags);
        project.setFireBaseId(firebaseId);
        return project;
    }

}
