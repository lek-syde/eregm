
package com.africapolicy.main.DTO;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orgUnit",
    "trackedEntityType",
    "enrollments",
    "attributes"
})
@Generated("jsonschema2pojo")
public class TrackedEntityInstance {

    @JsonProperty("orgUnit")
    private String orgUnit;
    @JsonProperty("trackedEntityType")
    private String trackedEntityType;



    @JsonProperty("enrollments")
    private List<Enrollment> enrollments;

    @JsonProperty("attributes")
    private List<Attribute> attributes;



    @JsonProperty("orgUnit")
    public String getOrgUnit() {
        return orgUnit;
    }

    @JsonProperty("orgUnit")
    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }


    @JsonProperty("trackedEntityType")
    public String getTrackedEntityType() {
        return trackedEntityType;
    }

    @JsonProperty("trackedEntityType")
    public void setTrackedEntityType(String trackedEntityType) {
        this.trackedEntityType = trackedEntityType;
    }



    @JsonProperty("enrollments")
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    @JsonProperty("enrollments")
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }



    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }






    public TrackedEntityInstance() {
    }


}
