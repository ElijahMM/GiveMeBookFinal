package com.mihai.licenta.Controllers;

import com.mihai.licenta.Repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mihai on 15.06.2017.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryToPrefController {

    @Autowired
    CategoryRepository categoryRepository;


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllCategories() {
        return ResponseEntity.ok(categoryRepository.getAll());
    }
}
