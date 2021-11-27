package com.africapolicy.main.repo;

import com.africapolicy.main.entity.Healthcenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Olalekan Folayan
 */
public interface HealthCenterRepo extends JpaRepository<Healthcenter,Integer > {

    Healthcenter findByHealthCenterId(long healthcenterid);


    List<Healthcenter> findByStateAndLga(String stateid, String lgaid);

    List<Healthcenter> findByLgaAndFacilitytype(String lga, String facilitytype);

    @Query(value="SELECT * FROM healthcenter u WHERE u.lga = ?1",
            nativeQuery = true)
    List<Healthcenter> findByLga(String lgaid);



    @Query(value="SELECT * FROM healthcenter u WHERE u.state = ?1 and u.facilitytype = ?2 group by u.lga order by u.lga asc",
            nativeQuery = true)
    List<Healthcenter> findDistinctByStateAndFacilitytype(String stateid, String facilitytype);

    @Query(value="SELECT distinct state FROM healthcenter u where u.facilitytype= ?1 ORDER BY u.state asc ",
            nativeQuery = true)
    List<String> findDistinctAll(String facilitytype);





    //List<Healthcenter> findByWardId(int wardid);
    //List<Healthcenter> findByStateAndLgaAndWar(int stateid, int lgaid, int wardid);
    //List<Healthcenter> findByStateIdOrWardIdOrLgaId(int stateid, int wardid, int lgaid);

}
