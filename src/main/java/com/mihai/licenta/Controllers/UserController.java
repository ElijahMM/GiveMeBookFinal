package com.mihai.licenta.Controllers;

import com.mihai.licenta.Models.DBModels.Interactions;
import com.mihai.licenta.Models.DBModels.User;
import com.mihai.licenta.Models.DBModels.UserPreferences;
import com.mihai.licenta.Models.InternModels.*;
import com.mihai.licenta.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihai on 15.05.2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam(value = "photo", required = false) MultipartFile file) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        if (userService.registerUser(user, file)) {
            return ResponseEntity.status(HttpStatus.OK).body(new StringResonse("User registered"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("User already register"));
    }


    @RequestMapping(value = "/registerFacebook", method = RequestMethod.POST)
    public ResponseEntity registerFacebookUser(@RequestBody RegisterUser user) {
        userService.registerFacebookUser(user);
        return ResponseEntity.ok(userService.findUserByEmail(user.getEmail()));
    }

    @RequestMapping(value = "/updatePassword/{id}", method = RequestMethod.POST)
    public ResponseEntity updatePassword(@PathVariable("id") Long userID, @RequestBody String password) {
        if (userService.changePassword(userID, password)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Fail"));
    }

    @RequestMapping(value = "/logout/{id}", method = RequestMethod.POST)
    public ResponseEntity logoutUser(@PathVariable("id") Long uid) {
        if (userService.removeUserToken(uid)) {
            return ResponseEntity.ok().body(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Invalid User"));
    }

    @RequestMapping(value = "/updatePreferences/{id}", method = RequestMethod.POST)
    public ResponseEntity updatePreferences(@RequestBody List<PreferanceIncomming> preferences, @PathVariable("id") Long uid) {
        List<UserPreferences> userPreferencesList = new ArrayList<>();
        for (PreferanceIncomming p : preferences) {
            UserPreferences u = new UserPreferences();
            u.setPname(p.getPname());
            userPreferencesList.add(u);
        }
        if (userService.updatePreferences(userPreferencesList, uid)) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(uid).getPreferences());
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/updatePhoto", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateUserPhoto(@RequestParam("id") Long uid, @RequestParam("photo") MultipartFile file) {
        String url = userService.updateUserPhoto(uid, file);
        if (url != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new StringResonse(url));
        }
        return ResponseEntity.badRequest().body(new StringResonse("No user with such id"));
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserByID(@PathVariable("id") Long uid) {
        SharedUser sharedUser = userService.getUserById(uid);
        if (sharedUser != null) {
            return ResponseEntity.ok().body(sharedUser);
        }
        return ResponseEntity.badRequest().body(new StringResonse("No user with such id"));
    }

    @RequestMapping(value = "/addInteraction", method = RequestMethod.POST)
    public ResponseEntity addInteraction(@RequestBody InteractionIncoming interactionIncoming) {
        Interactions interactions = userService.addInteraction(interactionIncoming);
        if (interactions != null) {
            return ResponseEntity.ok(interactions);
        }
        return ResponseEntity.badRequest().body(new StringResonse("No user with such id or friend already added"));
    }

    @RequestMapping(value = "/removeInteraction", method = RequestMethod.POST)
    public ResponseEntity removeInteraction(@RequestBody InteractionIncoming interactionIncoming) {
        if (userService.removeInteraction(interactionIncoming)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("No user with such id or friend already added"));
    }

}
