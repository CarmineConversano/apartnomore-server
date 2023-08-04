package org.apartnomore.server.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateComment {
    @NotNull
    @Size(max = 30000)
    private String text;

    public CreateComment(String text) {
        this.text = text;
    }

    public CreateComment() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
