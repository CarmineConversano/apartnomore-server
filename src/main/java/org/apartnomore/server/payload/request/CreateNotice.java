package org.apartnomore.server.payload.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateNotice {
    @NotEmpty
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 30000)
    private String description;

    public CreateNotice() {

    }

    public CreateNotice(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
