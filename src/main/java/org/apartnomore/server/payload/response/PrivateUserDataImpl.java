package org.apartnomore.server.payload.response;

import org.apartnomore.server.models.Address;
import org.apartnomore.server.models.Role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PrivateUserDataImpl implements PrivateUserData {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String avatarUrl;
    private String phoneNumber;
    private Set<Role> roles = new HashSet<>();
    private Address address;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public Set<Role> getRoles() {
        return this.roles;
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public PrivateUserDataImpl(Long id, String username, String email, String name, String surname, String avatarUrl, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
    }

    public PrivateUserDataImpl(PrivateUserData user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.avatarUrl = user.getAvatarUrl();
        this.phoneNumber = user.getPhoneNumber();
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
