package org.apartnomore.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = "name"))
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EPermission name;

    @OneToMany(mappedBy = "permission", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities;

    public Permission() {

    }

    public Permission(EPermission name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EPermission getName() {
        return name;
    }

    public void setName(EPermission name) {
        this.name = name;
    }

    public Set<UserCommunityPermissionsEntity> getUserCommunityPermissionsEntities() {
        return userCommunityPermissionsEntities;
    }

    public void setUserCommunityPermissionsEntities(Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities) {
        this.userCommunityPermissionsEntities = userCommunityPermissionsEntities;
    }
}
