package org.apartnomore.server.payload.response;

public class UploadResponse extends Result {

    private String path;
    private Long id;

    public UploadResponse(String detail, String path, Long id) {
        super(detail);
        this.path = path;
        this.id = id;
    }

    public String getData() {
        return path;
    }

    public void setData(String path) {
        this.path = path;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
