package org.apartnomore.server.payload.response;

public class JoinedCommunityResponse {
    private String communityName;
    private String message;

    public JoinedCommunityResponse(String communityName, String message) {
        this.communityName = communityName;
        this.message = message;
    }

    public JoinedCommunityResponse() {
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
