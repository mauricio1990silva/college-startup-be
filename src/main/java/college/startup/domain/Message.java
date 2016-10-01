package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Id
    @Column(name="message_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    public Message(User user, Project project, String content){
        this.content = content;
        this.user = user;
        this.project = project;
        this.status = "PENDING";
    }

    public Message(){}
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
