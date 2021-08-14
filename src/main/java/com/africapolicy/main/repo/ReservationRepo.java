package com.africapolicy.main.repo;

import com.africapolicy.main.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Olalekan Folayan
 */
public interface ReservationRepo extends JpaRepository<Reservation, Long> {


    List<Reservation> findByDate(java.sql.Date date);

    List<Reservation> findAll();

    List<Reservation> findByRoomIdAndTimeslot(Long roomId, String timeslot);

    List<Reservation> findByRoomIdAndTimeslotAndDate(Long roomid, String timeslot, Date date);
}
