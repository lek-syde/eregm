package com.africapolicy.main.DTO;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "resp_code",
        "resp_msg",
        "booking_ref_id",
        "payment_url"
})
@Generated("jsonschema2pojo")
public class LagosPaymentResponse {

    @JsonProperty("resp_code")
    public String resp_Code;
    @JsonProperty("resp_msg")
    public String resp_Msg;
    @JsonProperty("booking_ref_id")
    public String booking_ref_id;
    @JsonProperty("payment_url")
    public String payment_url;

    public String getResp_Code() {
        return resp_Code;
    }

    public void setResp_Code(String resp_Code) {
        this.resp_Code = resp_Code;
    }

    public String getResp_Msg() {
        return resp_Msg;
    }

    public void setResp_Msg(String resp_Msg) {
        this.resp_Msg = resp_Msg;
    }

    public String getBooking_ref_id() {
        return booking_ref_id;
    }

    public void setBooking_ref_id(String booking_ref_id) {
        this.booking_ref_id = booking_ref_id;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    @Override
    public String toString() {
        return "LagosPaymentResponse{" +
                "resp_Code='" + resp_Code + '\'' +
                ", resp_Msg='" + resp_Msg + '\'' +
                ", booking_ref_id='" + booking_ref_id + '\'' +
                ", payment_url='" + payment_url + '\'' +
                '}';
    }
}