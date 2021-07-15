package ru.dpoz.socnetw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.DateUtils;
import ru.dpoz.socnetw.model.HobbyEntity;
import ru.dpoz.socnetw.model.UserEntity;
import ru.dpoz.socnetw.repository.intf.Hobby;
import ru.dpoz.socnetw.repository.intf.User;
import ru.dpoz.socnetw.repository.intf.UserFriends;
import ru.dpoz.socnetw.repository.intf.UserHobbies;
import ru.dpoz.socnetw.security.UserSecretDetails;
import ru.dpoz.socnetw.service.intf.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class PagesController
{
    @Autowired
    private Hobby hobbyList;
    @Autowired
    private AuthService authService;
    @Autowired
    private User userDAO;
    @Autowired
    private UserHobbies userHobbiesDAO;
    @Autowired
    private UserFriends userFriendsDAO;

    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("time", DateUtils.createNow().getTime());
        UserSecretDetails currentUser = authService.getCurrentUser();
        boolean loggedIn = currentUser != null;
        model.addAttribute("loggedin", loggedIn);
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("hobbyList", this.hobbyList.getAll());
        UserSecretDetails currentUser = authService.getCurrentUser();
       /* if (currentUser != null)
            return "redirect:/user";*/
        return "signup/index";
    }

    @GetMapping({"/signin", "/signin?error"})
    public String signin()
    {
        UserSecretDetails currentUser = authService.getCurrentUser();
       /* if (currentUser != null)
            return "redirect:/user";*/
        return "signin/index";
    }

    @GetMapping({"/user" ,"/user/{userId}"})
    public String userPage(@PathVariable(required = false) UUID userId, Model model)
    {
        String username = "";
        UserEntity user = new UserEntity();
        List<UserEntity> friends = new ArrayList<>();
        List<HobbyEntity> hobbies = new ArrayList<>();
        UserSecretDetails currentUser = authService.getCurrentUser();
        boolean willViewSelf = userId == null;
        // Просмотр личного профиля, иначе - профиль другого пользователя
        if (willViewSelf){
            if (currentUser != null) {
                userId = currentUser.getUserSecretEntity().getUserId();
                username = currentUser.getUsername();
            }
        }
        else {
            username = ""; // не показываем чужой логин
        }

        if (userId != null) {
            user = this.userDAO.get(userId);
            hobbies = this.userHobbiesDAO.get(userId);
            friends = this.userFriendsDAO.getUsers(userId);
        }
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        model.addAttribute("hobbies", hobbies);
        model.addAttribute("friends", friends);
        return "user/index";
    }

    @GetMapping("/users")
    public String users(Model model)
    {
        List<UserEntity> userList = this.userDAO.getAll();
        model.addAttribute("users", userList);
        return "users/index";
    }

}
