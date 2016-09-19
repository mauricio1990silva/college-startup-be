//package college.startup.domain;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "project_group", catalog = "college_startup",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "project_group_id"}))
//@NoArgsConstructor
//@Data
//public class ProjectGroup implements Serializable {
//
//    @Id
//    @Column(name = "project_group_id", unique = true, nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @NotNull
//    @ManyToOne
//    @JsonIgnore
//    private User user;
//
//    @OneToOne
//    @PrimaryKeyJoinColumn
//    private Project project;
//
//    @Setter
//    @Getter
//    @NotNull
//    private String user_role;
//
//
//
//
//    public ProjectGroup(User user, Project project, String user_role) {
//        this.user = user;
//        this.project = project;
//        this.user_role = user_role;
//    }
//}
