package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "project_comment", catalog = "college_startup")
@Data
@NoArgsConstructor
public class ProjectComment {

    @Id
    @Column(name="comment_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    public ProjectComment(String  content){
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
