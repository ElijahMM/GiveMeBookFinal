package com.mihai.licenta.Repos;

import com.mihai.licenta.Models.DBModels.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Created by mihai on 15.06.2017.
 */
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    @Transactional
    @Query(nativeQuery = true, value = "SELECT DISTINCT(c.name) FROM categories c")
    Set<String> getAll();


    @Transactional
    @Query(nativeQuery = true, value = "SELECT c.book_id FROM categories c WHERE c.name = :cName")
    List<BigInteger> getBooks(@Param("cName") String catName);

}
