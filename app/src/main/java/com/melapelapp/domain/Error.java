package com.melapelapp.domain;

/**
 * Created by gesban on 10/12/2015.
 */
public class Error {
    private String cause;
    private String query;

    public Error()
    {
        this.setCause("");
        this.setQuery("");
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
