package com.africapolicy.main.repo;

import com.africapolicy.main.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Olalekan Folayan
 */
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
    public UserProfile findByUserId(Long geustId);
    public UserProfile findByVac(String vacKey);
    UserProfile findByPaymentid(String payid);

}
