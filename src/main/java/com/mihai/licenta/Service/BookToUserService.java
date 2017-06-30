package com.mihai.licenta.Service;

import com.mihai.licenta.Models.DBModels.BookState;
import com.mihai.licenta.Models.InternModels.BookStateIncoming;

import java.util.List;

/**
 * Created by mihai on 15.06.2017.
 */
public interface BookToUserService {

    Boolean addBookState(BookStateIncoming bookStateIncoming);

    Boolean removeBookState(BookStateIncoming bookStateIncoming);

    BookState getBookStateById(Long bid);

    List<BookState> getUserBookState(Long userID);
}
