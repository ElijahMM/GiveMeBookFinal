package com.mihai.licenta.Repos;

import com.mihai.licenta.Models.DBModels.Recommendations;
import com.mihai.licenta.Models.DBModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by mihai on 16.06.2017.
 */
public interface RecommendationRepository extends JpaRepository<Recommendations, Long> {

    List<Recommendations> findAllByUser(User user);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM recommendations where user_id =:userID")
    void removeUserRecommandtions(@Param("userID") Long uid);
}
