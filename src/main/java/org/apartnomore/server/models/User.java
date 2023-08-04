package org.apartnomore.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @NotEmpty
    @Size(min = 3, max = 30)
    private String username;

    @NotEmpty
    @Size(max = 320)
    @Email
    private String email;

    @NotEmpty
    @Size(max = 255)
    private String password;

    @ManyToMany()
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "userCommunities")
    @MapKey(name = "user")
    @JsonBackReference
    private Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities;

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserCommunities> userCommunities;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", insertable = true, updatable = false)
    private Date created;


    public User() {

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserCommunityPermissionsEntity> getUserCommunityPermissionsEntities() {
        return userCommunityPermissionsEntities;
    }

    public void setUserCommunityPermissionsEntities(Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities) {
        this.userCommunityPermissionsEntities = userCommunityPermissionsEntities;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
