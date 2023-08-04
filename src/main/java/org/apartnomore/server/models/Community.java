package org.apartnomore.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "access_link"))
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 80)
    private String name;

    @NotNull
    private boolean isPublic;

    @NotNull
    @Size(max = 100)
    @Column(name = "access_link")
    private String accessLink;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 20)
    private String location;

    @NotNull
    @ManyToOne
    private Image image;

    @NotNull
    private String lat;

    @NotNull
    private String lng;

    @OneToMany(mappedBy = "userCommunities", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapKey(name = "community")
    @JsonBackReference
    private Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities;

    @OneToMany(mappedBy = "community", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Set<UserCommunities> userCommunities;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", insertable = true, updatable = false)
    private Date created;


    public Community() {
    }

    public Community(String name, boolean isPublic, String description, String location) {
        this.name = name;
        this.isPublic = isPublic;
        this.description = description;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getAccessLink() {
        return accessLink;
    }

    public void setAccessLink(String accessLink) {
        this.accessLink = accessLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<UserCommunityPermissionsEntity> getUserCommunityPermissionsEntities() {
        return userCommunityPermissionsEntities;
    }

    public void setUserCommunityPermissionsEntities(Set<UserCommunityPermissionsEntity> userCommunityPermissionsEntities) {
        this.userCommunityPermissionsEntities = userCommunityPermissionsEntities;
    }

    public Set<UserCommunities> getUserCommunities() {
        return userCommunities;
    }

    public void setUserCommunities(Set<UserCommunities> userCommunities) {
        this.userCommunities = userCommunities;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

