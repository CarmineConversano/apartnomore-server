package org.apartnomore.server.payload.response;

public interface CommunityLocation {
    Long getId();

    String getAccessLink();

    String getisPublic();

    String getDescription();

    String getLocation();

    String getLat();

    String getLng();

    String getName();

    double getDistance();

    String getImagePath();
}
