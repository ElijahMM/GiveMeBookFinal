package com.mihai.licenta.Impl;

import com.mihai.licenta.Models.DBModels.Book;
import com.mihai.licenta.Models.DBModels.BookReview;
import com.mihai.licenta.Models.DBModels.Categories;
import com.mihai.licenta.Models.DBModels.UserPreferences;
import com.mihai.licenta.Models.InternModels.BookReviewIncoming;
import com.mihai.licenta.Models.InternModels.BookReviewModifier;
import com.mihai.licenta.Models.InternModels.CreateReviewIncoming;
import com.mihai.licenta.Models.InternModels.HelpCategories;
import com.mihai.licenta.Repos.BooksRepository;
import com.mihai.licenta.Service.BookService;
import com.mihai.licenta.Service.RecommendationService;
import com.mihai.licenta.Service.UserService;
import com.mihai.licenta.Utils.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mihai on 02.06.2017.
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    UserService userService;

    @Autowired
    private RecommendationService recommendationService;

    private static String UPLOADED_FOLDER = "/home/mihai/Documents/Licenta/Server/Resources/Images/Books/";

    @Override
    public Book findBookById(Long bid) {
        return booksRepository.findOne(bid);
    }

    @Override
    public Book findBookByName(String name) {
        return booksRepository.findByTitle(name);
    }

    @Override
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public Boolean registerBook(Book book, MultipartFile file, List<HelpCategories> categoriesList, Long userID) {
        Book savedBook = this.saveBook(book);
        try {
            String url = "";
            if (file != null) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + savedBook.getbId() + "");
                Files.write(path, bytes);

                url = "http://" + Urls.BASE_URL + Urls.GET_COVER_URL + savedBook.getbId();

            } else {
                url = "http://" + Urls.BASE_URL + Urls.GET_COVER_URL + "book_default";
            }

            savedBook.setCover_photo(url);
            List<Categories> categories = new ArrayList<>();
            for (HelpCategories hc : categoriesList) {
                Categories c = new Categories();
                c.setBook(savedBook);
                c.setName(hc.getName());
                categories.add(c);
            }
            savedBook.setCategories(new HashSet<>(categories));

            if (userID != null) {
                savedBook.setUploaderID(userID);
                Long bid = this.saveBook(savedBook).getbId();
                recommendationService.addRecommendation(userID, bid);
                List<UserPreferences> preferences = new ArrayList<>();
                for (HelpCategories hc : categoriesList) {
                    if (!preferences.stream().anyMatch(item -> hc.getName().equals(item.getPname()))) {
                        UserPreferences userPreferences = new UserPreferences();
                        userPreferences.setPname(hc.getName());
                        preferences.add(userPreferences);
                    }
                }
                userService.updatePreferences(preferences, userID);

            } else {
                this.saveBook(savedBook);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Book saveBook(Book book) {
        return booksRepository.save(book);
    }


    @Override
    public int setUpUserBookUrl(String url, Long bookId) {
        return booksRepository.updateBookCoverUrl(url, bookId);
    }

    @Override
    public Boolean addBookReview(CreateReviewIncoming bookReview, Long bookID) {
        Book book = findBookById(bookID);
        if (book != null) {
            BookReview bookReview1 = new BookReview();
            bookReview1.setBook(book);
            bookReview1.setReviewContent(bookReview.getReviewContent());
            bookReview1.setUserID(bookReview.getUserID());
            book.getBookReviews().add(bookReview1);
            saveBook(book);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeBookReview(BookReviewIncoming bookReviewIncoming) {
        Book book = findBookById(bookReviewIncoming.getBookID());
        if (book != null) {
            try {
                book.getBookReviews().remove(book.getBookReviews().stream().filter(o -> o.getIdBookReview().equals((bookReviewIncoming.getReviewID()))).findFirst().get());
                saveBook(book);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean modifyBookReview(BookReviewModifier bookReviewModifier) {
        Book book = findBookById(bookReviewModifier.getBookID());
        if (book != null) {
            try {
                book.getBookReviews().stream().filter(o -> o.getIdBookReview().equals((bookReviewModifier.getReviewID()))).findFirst().get().setReviewContent(bookReviewModifier.getContent());
                saveBook(book);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

}
