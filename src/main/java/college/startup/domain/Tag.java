package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@ToString
public class Tag {

    @Id
    @Column(name="tag_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String content;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonIgnore
    private Set<UserProfile> userProfiles = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    public Tag(){};

    public Tag(String content){
        this.content = content;
    }


}
