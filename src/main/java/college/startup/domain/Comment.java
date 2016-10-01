package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @Column(name="comment_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    public Comment(String content, User user, Project project){
        this.content = content;
        this.user = user;
        this.project = project;
    }

    public Comment(){}
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
