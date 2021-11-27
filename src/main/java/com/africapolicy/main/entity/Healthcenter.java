package com.africapolicy.main.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Olalekan Folayan
 */

@Data
@Entity
public class Healthcenter {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long healthCenterId;

    String state;
    String lga;
    String healthCenter;
    String active;
    String organizationuit;
    String facilitytype;
    double onedose;
    double fulldose;






}
