package com.africapolicy.main.entity;

import lombok.Data;

/**
 * @author Olalekan Folayan
 */

@Data
public class VerificationEntity {

    String verificationID;

    String verificationEmail;



    public String getVerificationID() {
        return verificationID;
    }

    public void setVerificationID(String verificationID) {
        this.verificationID = verificationID;
    }

    public VerificationEntity() {

    }

    public String getVerificationEmail() {
        return verificationEmail;
    }

    public void setVerificationEmail(String verificationEmail) {
        this.verificationEmail = verificationEmail;
    }
}
