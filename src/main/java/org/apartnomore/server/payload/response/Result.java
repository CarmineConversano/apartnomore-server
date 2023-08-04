package org.apartnomore.server.payload.response;

/***
 * General class for mapping HTTP REST endpoint returned value
 */
public class Result {
    private String detail;

    public Result(String detail) {
        this.detail = detail;
    }

    public Result() {
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
