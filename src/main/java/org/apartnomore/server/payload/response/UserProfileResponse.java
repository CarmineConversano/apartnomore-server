package org.apartnomore.server.payload.response;

public class UserProfileResponse {
    private Long id;
    private String name;
    private String surname;
    private String avatarUrl;
    private String phoneNumber;

    public UserProfileResponse() {
    }

    public UserProfileResponse(String name, String surname, String avatarUrl, String phoneNumber, Long id) {
        this.name = name;
        this.surname = surname;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.id = id;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
