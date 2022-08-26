package com.vangelis.doms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ErrorResponse extends RuntimeException
{
    private String code;
    private String reason;
    private String details;
    private Date timestamp;

    @Override @JsonIgnore
    public String getMessage() {
        return super.getMessage();
    }

    @Override @JsonIgnore
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override @JsonIgnore
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
