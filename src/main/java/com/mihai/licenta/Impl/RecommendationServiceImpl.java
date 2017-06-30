package com.mihai.licenta.Impl;


import com.mihai.licenta.Models.DBModels.*;
import com.mihai.licenta.Repos.CategoryRepository;
import com.mihai.licenta.Repos.RecommendationRepository;
import com.mihai.licenta.Service.BookService;
import com.mihai.licenta.Service.RecommendationService;
import com.mihai.licenta.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by mihai on 16.06.2017.
 */
@Service("recommendationService")
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryRepository categoryRepository;

    public void insertRecommendations(Long userID) {


        User user = userService.findUserById(userID);
        recommendationRepository.removeUserRecommandtions(userID);
        List<Book> allBooks = bookService.getAllBooks();
        List<Categories> categoriesList = categoryRepository.findAll();
        List<Book> recommendedBooks = new ArrayList<>();

        Set<String> userPreffs = user.getPreferences().stream()
                .map(UserPreferences::getPname)
                .collect(Collectors.toSet());

        List<Categories> categories = categoriesList.stream()
                .filter(cat -> userPreffs.contains(cat.getName()))
                .collect(Collectors.toList());

        List<BigInteger> booksID = new ArrayList<>();
        for (Categories c : categories) {
            booksID.addAll(categoryRepository.getBooks(c.getName()));
        }

        for (BigInteger id : booksID) {
            recommendedBooks.add(allBooks.stream().filter(b -> b.getbId().equals(id.longValue())).findFirst().get());
        }

        for (Book book : recommendedBooks) {
            saveRecommendation(user, book);
        }
    }

    private void saveRecommendation(User user, Book book) {
        List<Recommendations> alreadyRec = this.getRecommendationLocal(user.getUid());
        if (alreadyRec.stream().noneMatch(item -> item.getUser().getUid() == user.getUid() && item.getBook().getbId() == book.getbId())) {
            Recommendations recommendations = new Recommendations();
            recommendations.setBook(book);
            recommendations.setUser(user);
            user.getRecommendations().add(recommendations);
            book.getRecommendations().add(recommendations);
            recommendationRepository.save(recommendations);
        }
    }


    @Override
    public List<Recommendations> getRecommendations(Long userID) {
        insertRecommendations(userID);
        return recommendationRepository.findAllByUser(userService.findUserById(userID));
    }

    public List<Recommendations> getRecommendationLocal(Long userID) {
        return recommendationRepository.findAllByUser(userService.findUserById(userID));
    }

    @Override
    public Boolean addRecommendation(Long userID, Long bookID) {
        User user = userService.findUserById(userID);
        if (user != null) {
            Book book = bookService.findBookById(bookID);
            if (book != null) {
                saveRecommendation(user, book);
                return true;
            }
        }
        return false;
    }
}
