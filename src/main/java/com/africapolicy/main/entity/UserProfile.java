package com.africapolicy.main.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Olalekan Folayan
 */
@Data
@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;


    String surname;
    String firstname;
    String othername;
    String dateofbirth;
    String gender;
    String email;
    String phone;
    String phoneconf;
    String pregnant;

    String date;



    String frontline;
    String occupation;
    String healthrelated;
    String area;
    boolean medicalconditionpresent;
    String stateresidence;
    String lga;
    long facilityid;
    String healthfacility;
    String session;
    long estimatedage;
    String idtype;
    String resaddress;
    String vac;
    String orgid;
    String knowdob="false";

    String facilitytype;
    String dosetype;

    double cost;
    String paid;

    String paymentref;

    String transid;
    String paymentid;



    public String getKnowdob() {
        return knowdob;
    }

    public void setKnowdob(String knowdob) {
        this.knowdob = knowdob;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
}
