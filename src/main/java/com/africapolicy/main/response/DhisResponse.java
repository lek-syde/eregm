package com.africapolicy.main.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

/**
 * @author Olalekan Folayan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "httpStatus",
        "httpStatusCode",
        "status",
        "message",
        "response"
})
@Generated("jsonschema2pojo")
public class DhisResponse {



    @JsonProperty("httpStatus")
    private String httpStatus;
    @JsonProperty("httpStatusCode")
    private Integer httpStatusCode;
    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;



    @JsonProperty("httpStatus")
    public String getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty("httpStatus")
    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    @JsonProperty("httpStatusCode")
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    @JsonProperty("httpStatusCode")
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

}