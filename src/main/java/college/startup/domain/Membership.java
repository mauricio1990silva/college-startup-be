package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "membership",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "project_id"}))
@NoArgsConstructor
@Data
public class Membership implements Serializable {

    @Id
    @Column(name="membership_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @NotNull
    private String status;

    @NotNull
    @ManyToOne
    @JoinColumn(name="project_id")
    @JsonIgnore
    private Project project;

    public Membership(User user, Project project, String status) {
        this.user = user;
        this.project = project;
        this.status = status;
    }
}
