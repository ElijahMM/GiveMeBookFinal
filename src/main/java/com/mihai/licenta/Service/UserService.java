package com.mihai.licenta.Service;

import com.mihai.licenta.Models.DBModels.Interactions;
import com.mihai.licenta.Models.DBModels.User;
import com.mihai.licenta.Models.DBModels.UserPreferences;
import com.mihai.licenta.Models.InternModels.InteractionIncoming;
import com.mihai.licenta.Models.InternModels.RegisterUser;
import com.mihai.licenta.Models.InternModels.SharedUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by mihai on 16.05.2017.
 */
public interface UserService {

    User findUserByEmail(String email);

    User findUserById(Long id);

    User findUserByUsername(String username);

    User saveUser(User user, Integer createOrUpdate);

    int setUpUserPhotoUrl(String url, Long userId);

    User loginUser(String email, String password);

    Boolean registerUser(User user, MultipartFile file);

    Boolean registerFacebookUser(RegisterUser user);

    Boolean changePassword(Long userID, String password);

    Boolean updatePreferences(List<UserPreferences> prefs, Long uid);

    String updateUserPhoto(Long uid, MultipartFile file);

    Boolean removeUserToken(Long uid);

    SharedUser getUserById(Long uid);

    Interactions addInteraction(InteractionIncoming interactionIncoming);

    Boolean removeInteraction(InteractionIncoming interactionIncoming);



}
