package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile", catalog = "college_startup")
@ToString
public class UserProfile  {

    @Id
    @Column(name="user_id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign",
            parameters=@Parameter(name="property", value="user"))
    @Getter
    @Setter
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    @JsonIgnore
    private User user;

    @NotNull
    @Getter
    @Setter
    private String major;

    @NotNull
    @Getter
    @Setter
    private String year;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_profile_tags", catalog = "college_startup", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "tag_id",
                    nullable = false, updatable = false) })
    private Set<Tag> tags = new HashSet<>();


    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


}
