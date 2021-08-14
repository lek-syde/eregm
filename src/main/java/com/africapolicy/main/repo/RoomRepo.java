package com.africapolicy.main.repo;

import com.africapolicy.main.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Olalekan Folayan
 */
public interface  RoomRepo extends JpaRepository<Room, Long> {

    List<Room> findAll();


    @Query(value="SELECT * FROM room u WHERE u.NAME = ?1 group by u.TIMESLOT",
            nativeQuery = true)
    List<Room> findByName(String name);

    Room findById(long id);

    Room findByHealthCenterIdAndTimeslot(long id, String timeslot);

    List<Room> findByHealthCenterId(long id);









}
