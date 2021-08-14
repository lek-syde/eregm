package com.africapolicy.main;

import com.africapolicy.main.entity.UserProfile;
import lombok.Data;

import java.util.Date;

@Data
public class RoomReservation {
	private long roomId;
	private String roomName;
	private String timeslot;
	private Date date;

	private String firstName;
	private String lastName;
	private int total;
	

	
	
	
}
