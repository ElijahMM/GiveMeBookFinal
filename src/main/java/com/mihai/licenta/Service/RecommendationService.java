package com.mihai.licenta.Service;

import com.mihai.licenta.Models.DBModels.Recommendations;

import java.util.List;

/**
 * Created by mihai on 16.06.2017.
 */
public interface RecommendationService {


    List<Recommendations> getRecommendations(Long userID);

    Boolean addRecommendation(Long userID, Long bookID);
}
