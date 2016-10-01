package college.startup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "user", catalog = "college_startup", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@ToString
public class User implements UserDetails {

    @Id
    @Column(name="user_id", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Long id;

    @NotNull
    @Size(min = 4, max = 30)
    @Setter
    @Getter
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String username;

    @JsonIgnore
    @NotNull
    private String password;

    @Getter
    @Setter
    private String registration_status = "PENDING";

    @NotNull
    @Size(min = 4, max = 30)
    @Getter
    @Setter
    private String name;

    @Column(name="firebase_id")
    @Getter
    @Setter
    @JsonIgnore
    private String fireBaseId;


    @NotNull
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @Getter
    @Setter
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL)
    private UserProfile userProfile;



    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;


    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Project> projects;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Message> messages;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "liked_project", catalog = "college_startup", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "project_id",
                    nullable = false, updatable = false) })
    @JsonIgnore
    private Set<Project> likes = new HashSet<>();


    @Getter
    @Setter
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Relationship> followerRelations;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Membership> projectMembership;




    @Getter
    @Setter
    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Relationship> followedRelations;

    @Override
    @JsonProperty("email")
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_USER");
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
