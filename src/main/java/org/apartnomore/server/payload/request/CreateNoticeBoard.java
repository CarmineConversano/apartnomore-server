package org.apartnomore.server.payload.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class CreateNoticeBoard {
    @NotEmpty
    @Size(max = 255)
    String name;

    public CreateNoticeBoard(String name) {
        this.name = name;
    }

    public CreateNoticeBoard() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
