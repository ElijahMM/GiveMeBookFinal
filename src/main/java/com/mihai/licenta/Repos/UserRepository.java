package com.mihai.licenta.Repos;

import com.mihai.licenta.Models.DBModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by mihai on 12.04.2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User findUserByEmailAndPassword(String email, String password);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE  user u SET u.token=null WHERE u.uid =:userID")
    void removerToken(@Param("userID") Long userID);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE user u SET u.photo_url = :url WHERE u.uid = :userID")
    int updateUserPhotoUrl(@Param("url") String url, @Param("userID") Long userId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "UPDATE user u SET u.TYPE =:tipU WHERE u.uid = :userID")
    void updateUserType(@Param("tipU") Integer type, @Param("userID") Long userID);
}
