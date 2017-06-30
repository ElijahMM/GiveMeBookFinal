package com.mihai.licenta.Impl;

import com.mihai.licenta.Models.DBModels.Book;
import com.mihai.licenta.Models.DBModels.BookState;
import com.mihai.licenta.Models.DBModels.User;
import com.mihai.licenta.Models.InternModels.BookStateIncoming;
import com.mihai.licenta.Repos.BookStateRepository;
import com.mihai.licenta.Service.BookService;
import com.mihai.licenta.Service.BookToUserService;
import com.mihai.licenta.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mihai on 15.06.2017.
 */
@Service("bookToUserService")
public class BookToUserServiceImpl implements BookToUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookStateRepository bookStateRepository;

    @Override
    public Boolean addBookState(BookStateIncoming bookStateIncoming) {
        User user = userService.findUserById(bookStateIncoming.getUserID());
        Book book = bookService.findBookById(bookStateIncoming.getBookID());

        if (user != null && book != null) {
            BookState bookState = bookStateRepository.findBookStateByUserAndBook(user, book);
            if (bookState == null) {
                bookState = new BookState();
                bookState.setType(bookStateIncoming.getType());
                bookState.setUser(user);
                bookState.setBook(book);
                book.getBookStates().add(bookState);
                user.getBookStates().add(bookState);
                bookStateRepository.save(bookState);
                return true;
            } else {
                bookState.setType(bookStateIncoming.getType());
                bookStateRepository.save(bookState);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean removeBookState(BookStateIncoming bookStateIncoming) {
        User user = userService.findUserById(bookStateIncoming.getUserID());
        if (user != null) {
            try {
                user.getBookStates().remove(user.getBookStates().stream().filter(
                        o -> o.getBook().getbId().equals(bookStateIncoming.getBookID()) && o.getType().equals(bookStateIncoming.getType())).findFirst().get());
                userService.saveUser(user, 1);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    @Override
    public BookState getBookStateById(Long bid) {
        return bookStateRepository.findOne(bid);
    }

    @Override
    public List<BookState> getUserBookState(Long userID) {
        User user = userService.findUserById(userID);
        if (user != null) {
            return bookStateRepository.getBookStateByUser(user);
        }
        return null;
    }

}
