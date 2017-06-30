package com.mihai.licenta.Controllers;

import com.mihai.licenta.Models.DBModels.BookState;
import com.mihai.licenta.Models.InternModels.BookStateIncoming;
import com.mihai.licenta.Models.InternModels.StringResonse;
import com.mihai.licenta.Service.BookToUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mihai on 15.06.2017.
 */
@RestController
@RequestMapping("/api/booktouser")
public class BookToUserController {

    @Autowired
    BookToUserService bookToUserService;


    @RequestMapping(value = "/addBookState", method = RequestMethod.POST)
    public ResponseEntity addBookState(@RequestBody BookStateIncoming bookStateIncomming) {
        if (bookToUserService.addBookState(bookStateIncomming)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Book id or user id invalid"));
    }

    @RequestMapping(value = "/getBookStateById/{id}", method = RequestMethod.GET)
    public ResponseEntity getBookStateById(@PathVariable("id") Long bid) {
        BookState bookState = bookToUserService.getBookStateById(bid);
        if (bookState != null) {
            return ResponseEntity.ok(bookState);
        }
        return ResponseEntity.badRequest().body(new StringResonse("No bs with such id"));
    }


    @RequestMapping(value = "/getBookStateByUser/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserStateBy(@PathVariable("id") Long uid) {
        List<BookState> bookState = bookToUserService.getUserBookState(uid);
        if (bookState != null) {
            return ResponseEntity.ok(bookState);
        }
        return ResponseEntity.badRequest().body(new StringResonse("No bs with such id"));
    }

    @RequestMapping(value = "/removeBookState", method = RequestMethod.DELETE)
    public ResponseEntity removerBookState(@RequestBody BookStateIncoming bookStateIncoming) {
        if (bookToUserService.removeBookState(bookStateIncoming)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Book id or user id invalid"));
    }

}
