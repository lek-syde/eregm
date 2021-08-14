package com.africapolicy.main.service;

import com.africapolicy.main.RoomReservation;
import com.africapolicy.main.entity.Reservation;
import com.africapolicy.main.entity.Room;
import com.africapolicy.main.entity.UserProfile;
import com.africapolicy.main.repo.ReservationRepo;
import com.africapolicy.main.repo.RoomRepo;
import com.africapolicy.main.repo.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Olalekan Folayan
 */
@Service
public class RoomReservationService {
    private RoomRepo roomRepository;
    private UserProfileRepo userProfileRepo;
    private ReservationRepo reservationRepository;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public RoomReservationService(RoomRepo roomRepository, UserProfileRepo userProfileRepo,
                                  ReservationRepo reservationRepository) {
        this.roomRepository = roomRepository;
        this.userProfileRepo = userProfileRepo;
        this.reservationRepository = reservationRepository;
    }

    /**
     *
     * @param	dateString	a string represents date such as 2019-10-08
     * @return				an array list of roomReservation
     */
    public List<RoomReservation> getRoomReservationsForDate(String dateString) {
        Date date = createDateFromDateString(dateString);
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();

        Iterable<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setTimeslot(room.getTimeslot());

            roomReservationMap.put(room.getId(), roomReservation);
        });

        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        if (reservations != null) {




            reservations.forEach(reservation -> {

                List<Reservation> reservs= this.reservationRepository.findByRoomIdAndTimeslotAndDate(reservation.getRoomId(), reservation.getTimeslot(), new java.sql.Date(date.getTime()));

                int reservat= reservs.size();
                UserProfile guest = this.userProfileRepo.findByUserId(reservation.getUserId());


                if (guest != null) {
                    System.out.println(guest.getFirstname());

                    System.out.println(reservation.getId());
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());

                    System.out.println(roomReservation.toString());
                    roomReservation.setFirstName(guest.getSurname());
                    roomReservation.setLastName(guest.getFirstname());
                    roomReservation.setDate(new java.sql.Date(date.getTime()));
                    roomReservation.setTotal(reservat);
                }
            });
        }

        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long roomId : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }

    /**
     * @param	dateString	a string represents date, such as 2019-10-08
     * @return 				a Date format date
     */
    private Date createDateFromDateString(String dateString) {

        Date date = null;
        if(null != dateString) {
            try {
                date = DATE_FORMAT.parse(dateString);
            }catch (ParseException pe) {
                date = new Date();
            }
        } else {
            date = new Date();
        }

        return date;
    }
}
