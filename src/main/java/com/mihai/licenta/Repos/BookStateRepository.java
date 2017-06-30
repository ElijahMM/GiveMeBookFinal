package com.mihai.licenta.Repos;

import com.mihai.licenta.Models.DBModels.Book;
import com.mihai.licenta.Models.DBModels.BookState;
import com.mihai.licenta.Models.DBModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mihai on 15.06.2017.
 */
public interface BookStateRepository extends JpaRepository<BookState, Long> {


    List<BookState> getBookStateByUser(User user);
    BookState findBookStateByUserAndBook(User user, Book book);
}
