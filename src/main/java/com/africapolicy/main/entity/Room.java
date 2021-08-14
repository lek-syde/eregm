package com.africapolicy.main.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ROOM")
public class Room {
    @Id
    @Column(name="ROOM_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="NAME")
    private String name;

    @Column(name="TIMESLOT")
    private String timeslot;
    @Column(name="QUANTITY")
    private int quantity;

    private long healthCenterId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getHealthCenterId() {
        return healthCenterId;
    }

    public void setHealthCenterId(long healthCenterId) {
        this.healthCenterId = healthCenterId;
    }
}
