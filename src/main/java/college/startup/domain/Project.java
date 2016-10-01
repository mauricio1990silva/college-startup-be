package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
@ToString
public class Project implements Serializable {

    @Id
    @Column(name = "project_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String type;

    @NotNull
    @Getter
    @Setter
    private String name;

    @NotNull
    @Getter
    @Setter
    private String content;

    @Column(name="firebase_id")
    @Getter
    @Setter
    @JsonIgnore
    private String fireBaseId;

    @Column(name = "members_required")
    @Getter
    @Setter
    private int membersRequired;


    @Column(name = "members_joined")
    @Getter
    @Setter
    private int membersJoined;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private User user;


    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
    private Set<User> likeBy = new HashSet<>(0);


    @Getter
    @Setter
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;


    @Getter
    @Setter
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Membership> projectMembership;


    @Getter
    @Setter
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Message> messages;


    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "project_tags", catalog = "college_startup", joinColumns = {
            @JoinColumn(name = "project_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "tag_id",
                    nullable = false, updatable = false) })
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();


    @NotNull
    @Column(name = "created_at")
    @Getter
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
