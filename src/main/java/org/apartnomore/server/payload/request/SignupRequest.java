package org.apartnomore.server.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    @Size(min = 3, max = 320)
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    private UserProfileRequest profile;

    private AddressRequest address;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfileRequest getProfile() {
        return profile;
    }

    public void setProfile(UserProfileRequest profile) {
        this.profile = profile;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}
