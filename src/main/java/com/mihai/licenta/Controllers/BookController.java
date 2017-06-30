package com.mihai.licenta.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihai.licenta.Models.DBModels.Book;
import com.mihai.licenta.Models.InternModels.*;
import com.mihai.licenta.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihai on 07.06.2017.
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    BookService bookService;


    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ResponseEntity addBook(@RequestParam(value = "userID", required = false) Long userID,
                                  @RequestParam("bookTitle") String title,
                                  @RequestParam("author") String author,
                                  @RequestParam("description") String description,
                                  @RequestParam("categories") String sCategories,
                                  @RequestParam(value = "cover_photo", required = false) MultipartFile file) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        ObjectMapper mapper = new ObjectMapper();
        List<HelpCategories> aux = new ArrayList<>();
        try {
            aux = mapper.readValue(sCategories, new TypeReference<List<HelpCategories>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bookService.registerBook(book, file, aux, userID)) {
            return ResponseEntity.ok().body(new StringResonse("Success"));
        }

        return ResponseEntity.badRequest().body(new StringResonse("Something went wrong"));
    }


    @RequestMapping(value = "/addBookReview/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBookReview(@RequestBody CreateReviewIncoming bookReview, @PathVariable("id") Long bookID) {
        if (bookService.addBookReview(bookReview, bookID)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body("Invalid book id");
    }


    @RequestMapping(value = "/getBookByID/{id}", method = RequestMethod.GET)
    public ResponseEntity getBookByID(@PathVariable("id") Long bookId) {
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().body(new StringResonse("No book with such id"));
    }


    @RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
    public ResponseEntity getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @RequestMapping(value = "/removeBookReview", method = RequestMethod.DELETE)
    public ResponseEntity removeBookReview(@RequestBody BookReviewIncoming bookReviewIncoming) {
        if (bookService.removeBookReview(bookReviewIncoming)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Invalid ids"));
    }


    @RequestMapping(value = "/modifyBookReview", method = RequestMethod.PUT)
    public ResponseEntity modifyBookReview(@RequestBody BookReviewModifier bookReviewModifier) {
        if (bookService.modifyBookReview(bookReviewModifier)) {
            return ResponseEntity.ok(new StringResonse("Success"));
        }
        return ResponseEntity.badRequest().body(new StringResonse("Invalid ids"));
    }


}
