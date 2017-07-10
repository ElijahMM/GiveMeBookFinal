package com.mihai.licenta.Impl;

import com.mihai.licenta.Models.DBModels.Interactions;
import com.mihai.licenta.Models.DBModels.User;
import com.mihai.licenta.Models.DBModels.UserPreferences;
import com.mihai.licenta.Models.InternModels.InteractionIncoming;
import com.mihai.licenta.Models.InternModels.RegisterUser;
import com.mihai.licenta.Models.InternModels.SharedUser;
import com.mihai.licenta.Repos.UserRepository;
import com.mihai.licenta.Service.UserService;
import com.mihai.licenta.Utils.Urls;
import com.mihai.licenta.Utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;


/**
 * Created by mihai on 16.05.2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    private static String UPLOADED_FOLDER = "/home/mihai/Documents/Licenta/Server/Resources/Images/User/";

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Boolean registerUser(User user, MultipartFile file) {

        if ((findUserByEmail(user.getEmail())) == null) {
            if ((findUserByUsername(user.getUsername()) == null)) {
                Long id = this.saveUser(user, 0).getUid();
                String url;
                try {
                    if (file != null) {
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(UPLOADED_FOLDER + id + "");
                        Files.write(path, bytes);
                        url = "http://" + Urls.BASE_URL + Urls.GET_PHOTO_URL + id;
                    } else {
                        url = "http://" + Urls.BASE_URL + Urls.GET_PHOTO_URL + "def_img";
                    }
                    setUpUserPhotoUrl(url, id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean registerFacebookUser(RegisterUser user) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            User userR = new User();
            userR.setUsername(user.getUsername());
            userR.setFbID(user.getFbID());
            userR.setPhotoUrl(user.getPhotoUrl());
            userR.setPassword(Util.sha1Hash("root"));
            userR.setEmail(user.getEmail());
            saveUser(userR, 0);
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePassword(Long userID, String password) {
        User user = userRepository.findOne(userID);
        if (user != null) {
            user.setPassword(password);
            user.setUpdatedAt(Util.getSqlDate());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePreferences(List<UserPreferences> prefs, Long uid) {
        User user = findUserById(uid);
        if (user != null) {
            Set<UserPreferences> newPreferenceList = prefs.stream().filter(n -> !user.getPreferences().contains(n)).collect(Collectors.toSet());
            user.getPreferences().clear();
            for (UserPreferences p : newPreferenceList) {
                p.setUser(user);
                user.getPreferences().add(p);
            }
            saveUser(user, 1);
            return true;
        }
        return false;
    }

    @Override
    public Set<UserPreferences> getPrefByID(Long uid) {
       User user = findUserById(uid);
       if(user != null){
           return user.getPreferences();
       }
       return null;
    }


    @Override
    public User saveUser(User user, Integer createOrUpdate) {
        if (createOrUpdate == 0) {
            user.setType(0);
            user.setCreatedAt(Util.getSqlDate());
            user.setUpdatedAt(Util.getSqlDate());

        } else {
            user.setUpdatedAt(Util.getSqlDate());
        }
        return userRepository.save(user);
    }

    @Override
    public String updateUserPhoto(Long uid, MultipartFile file) {
        User user = findUserById(uid);
        String url = "";
        if (user != null) {
            try {
                if (file != null) {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(UPLOADED_FOLDER + user.getUid() + "");
                    Files.write(path, bytes);

                    url = "http://" + Urls.BASE_URL + Urls.GET_PHOTO_URL + user.getUid();

                    setUpUserPhotoUrl(url, user.getUid());
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }
        return null;
    }

    @Override
    public Boolean removeUserToken(Long uid) {
        if (userRepository.findOne(uid) != null) {
            userRepository.removerToken(uid);
            userRepository.updateUserType(0,uid);
            return true;
        }
        return false;
    }

    @Override
    public SharedUser getUserById(Long uid) {
        User user = findUserById(uid);
        if (user != null) {
            SharedUser sharedUser = new SharedUser();
            sharedUser.setUid(user.getUid());
            sharedUser.setUsername(user.getUsername());
            sharedUser.setPhotoUrl(user.getPhotoUrl());
            sharedUser.setBookStates(user.getBookStates());
            sharedUser.setInteractions(user.getInteractions());
            sharedUser.setFbID(user.getFbID());
            return sharedUser;
        }
        return null;
    }

    @Override
    public Interactions addInteraction(InteractionIncoming interactionIncoming) {
        User user = userRepository.findOne(interactionIncoming.getMyID());
        if (user != null) {
            if (userRepository.findOne(interactionIncoming.getFriendID()) != null) {
                if (!user.getInteractions().stream().filter(o -> o.getiId().equals(interactionIncoming.getFriendID()) && o.getType().equals(interactionIncoming.getType())).findFirst().isPresent()) {
                    Interactions interactions = new Interactions();
                    interactions.setRefId(interactionIncoming.getFriendID());
                    interactions.setUser(user);
                    interactions.setType(interactionIncoming.getType());
                    user.getInteractions().add(interactions);
                    saveUser(user, 1);
                    return interactions;
                }
            }
        }
        return null;
    }

    @Override
    public Boolean removeInteraction(InteractionIncoming interactionIncoming) {
        User user = findUserById(interactionIncoming.getMyID());
        if (user != null) {
            user.getInteractions().remove(user.getInteractions().stream().filter(o ->
                    o.getRefId().equals(interactionIncoming.getFriendID()) && o.getType().equals(interactionIncoming.getType())).findFirst().get()
            );
            saveUser(user, 1);
            return true;
        }
        return false;
    }


    @Override
    public int setUpUserPhotoUrl(String url, Long userId) {
        return userRepository.updateUserPhotoUrl(url, userId);
    }

    @Override
    public void changeUserType(Integer type, Long userId) {
        userRepository.updateUserType(type, userId);
    }

}
