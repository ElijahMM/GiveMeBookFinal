package com.mihai.licenta.Service;

import com.mihai.licenta.Models.DBModels.Book;
import com.mihai.licenta.Models.InternModels.BookReviewIncoming;
import com.mihai.licenta.Models.InternModels.BookReviewModifier;
import com.mihai.licenta.Models.InternModels.CreateReviewIncoming;
import com.mihai.licenta.Models.InternModels.HelpCategories;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by mihai on 02.06.2017.
 */

public interface BookService {

    Book findBookById(Long bid);

    Book findBookByName(String name);

    List<Book> getAllBooks();

    Book saveBook(Book book);

    Boolean registerBook(Book book, MultipartFile file, List<HelpCategories> categoriesList, Long userID);

    int setUpUserBookUrl(String url, Long bookId);

    Boolean addBookReview(CreateReviewIncoming bookReview, Long bookID);

    Boolean removeBookReview(BookReviewIncoming bookReviewIncoming);

    Boolean modifyBookReview(BookReviewModifier bookReviewModifier);

}
