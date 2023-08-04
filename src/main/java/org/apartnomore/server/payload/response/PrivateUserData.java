package org.apartnomore.server.payload.response;

import org.apartnomore.server.models.Address;
import org.apartnomore.server.models.Role;

import java.util.Set;

public interface PrivateUserData {
    Long getId();

    String getUsername();

    String getEmail();

    String getName();

    String getSurname();

    String getAvatarUrl();

    String getPhoneNumber();

    Set<Role> getRoles();

    Address getAddress();

}
