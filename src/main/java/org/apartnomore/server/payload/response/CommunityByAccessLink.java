package org.apartnomore.server.payload.response;

import org.apartnomore.server.models.Image;

public class CommunityByAccessLink {
    private Long id;
    private String name;
    private boolean isPublic;
    private String accessLink;
    private String description;
    private String location;
    private Image image;
    private boolean subscribed;

    public CommunityByAccessLink() {
    }

    public CommunityByAccessLink(Long id, String name, boolean isPublic, String accessLink, String description, String location, Image image, boolean subscribed) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.accessLink = accessLink;
        this.description = description;
        this.location = location;
        this.image = image;
        this.subscribed = subscribed;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
