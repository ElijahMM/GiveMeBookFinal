package com.mihai.licenta.Controllers;

import com.mihai.licenta.Service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mihai on 16.06.2017.
 */
@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity getRecommendations(@PathVariable("id") Long uid) {
        return ResponseEntity.ok(recommendationService.getRecommendations(uid));
    }
}
