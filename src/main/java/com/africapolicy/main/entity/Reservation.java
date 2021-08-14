package com.africapolicy.main.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
@Data
@Entity
@Table(name="RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RESERVATION_ID")
    private long id;

    @Column(name="ROOM_ID")
    private long roomId;




    @Column(name="User_ID")
    private long userId;

    @Column(name="RES_DATE")
    private Date date;

    private String timeslot;


}