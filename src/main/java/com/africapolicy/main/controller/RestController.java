package com.africapolicy.main.controller;

import com.africapolicy.main.*;
import com.africapolicy.main.entity.*;
import com.africapolicy.main.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Olalekan Folayan
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {


    @Autowired
    HealthCenterRepo healthCenterRepo;

    @Autowired
    RoomRepo roomRepo;

    @Autowired
    ReservationRepo reservationRepo;





    @GetMapping(value = "/getlga/{id}")
    public List<Healthcenter> getwards(@PathVariable("id") String stateid) {
        System.out.println("stateid"+ stateid);



        return healthCenterRepo.findDistinctByState(stateid);

    }




    @GetMapping(value = "/states")
    public List<Healthcenter> getstates() {
        return healthCenterRepo.findAll();

    }



//    @GetMapping(value = "/getward/{id}")
//    public List<Ward> getbyid(@PathVariable("id") int lgaid) {
//        return healthCenterRepo.findByLocalId(lgaid);
//
//    }


    @GetMapping(value = "/getroomsessions/{id}")
    public List<Room> getsessionbyname(@PathVariable("id") Long id) {
        if (id != null) {
                return roomRepo.findByHealthCenterId(id);
            }else{
                return new ArrayList<Room>();
            }

    }


    @GetMapping(value = "/checkavailability")
    public ResponseEntity<AvailabilityStatus> getreservationnumber(@RequestParam(name = "roomid", required = true) Long roomid,
                                                                   @RequestParam(name = "timeslot", required = true) String timeslot,
                                                                   @RequestParam(name = "date", required = true) String date) throws ParseException {

        Room room = roomRepo.findByHealthCenterIdAndTimeslot(roomid, timeslot);
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        int roomsize = room.getQuantity();

        System.out.println("roomid"+ roomid);
        System.out.println("timeslot"+ roomid);
        System.out.println("date"+ date);

        int currentguests = reservationRepo.findByRoomIdAndTimeslotAndDate(room.getId(), timeslot, date1).size();
        System.out.println("currentgueste" + currentguests);
        System.out.println("roomsize" + roomsize);

        if (currentguests >= roomsize) {
            return new ResponseEntity<>(new AvailabilityStatus("FULL"), HttpStatus.OK);

        }
        return new ResponseEntity<>(new AvailabilityStatus("OPEN"), HttpStatus.OK);

    }


    @GetMapping(value = "/getfacility")
    public List<Healthcenter> getfacility(
            @RequestParam(name = "lgaid", required = false) String lgaid,
            @RequestParam(name = "wardid", required = false) Integer wardid,
            @RequestParam(name = "stateid", required = false) String stateid) {

        System.out.println("here");
        if (stateid != null && lgaid == null && wardid == null) {
            System.out.println("here1");
            return healthCenterRepo.findDistinctByState(stateid);
        } else if (stateid != null && lgaid != null) {
            System.out.println("here2");
            return healthCenterRepo.findByStateAndLga(stateid, lgaid);
        } else if (stateid != null && lgaid != null && wardid != null) {
            System.out.println("here3");
          //  return healthCenterRepo.findByStateAndLgaAndWard(stateid, lgaid, wardid);
        }

        return healthCenterRepo.findByLga(lgaid);
    }




}
