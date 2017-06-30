package com.mihai.licenta.Repos;

import com.mihai.licenta.Models.DBModels.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by mihai on 12.04.2017.
 */
public interface BooksRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE books b SET b.cover_photo = :url WHERE b.b_id = :bookId")
    int updateBookCoverUrl(@Param("url") String url, @Param("bookId") Long bookId);
}
