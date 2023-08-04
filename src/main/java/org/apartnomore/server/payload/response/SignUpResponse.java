package org.apartnomore.server.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apartnomore.server.models.Role;

import java.util.Set;

public class SignUpResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    @JsonProperty("profile")
    private UserProfileResponse userProfileResponse;

    @JsonProperty("address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AddressResponse addressResponse;

    public SignUpResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserProfileResponse getUserProfileResponse() {
        return userProfileResponse;
    }

    public void setUserProfileResponse(UserProfileResponse userProfileResponse) {
        this.userProfileResponse = userProfileResponse;
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }

    public void setAddressResponse(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }
}
