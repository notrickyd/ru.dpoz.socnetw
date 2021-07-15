package ru.dpoz.socnetw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dpoz.socnetw.model.UserSecretEntity;
import ru.dpoz.socnetw.repository.intf.User;
import ru.dpoz.socnetw.repository.intf.UserHobbies;
import ru.dpoz.socnetw.repository.intf.UserSecret;
import ru.dpoz.socnetw.security.UserSecretDetails;
import ru.dpoz.socnetw.service.helpers.SignUpInfo;
import ru.dpoz.socnetw.service.intf.AuthService;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService
{
    @Autowired
    User user;
    @Autowired
    UserHobbies userHobbies;
    @Autowired
    UserSecret secret;

    @Override
    public UserSecretDetails getCurrentUser()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserSecretDetails) {
            return ((UserSecretDetails) principal);
        }
        else
            return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserSecretEntity userData = this.secret.findUsername(username);
        if (userData == null)
            throw new UsernameNotFoundException(username);
        return new UserSecretDetails(userData);
    }

    @Override
    public boolean checkUsernameExists(String username)
    {
        return  this.secret.findUsername(username) != null;
    }

    @Override
    @Transactional
    public boolean signUp(SignUpInfo signUpInfo)
    {
        //TODO проверка входящей информации, сервис верификации, try-catch и тд
        UUID userId =  user.add(signUpInfo.getUser());
        signUpInfo.getHobbies().forEach(id -> userHobbies.add(userId, id));
        signUpInfo.getSecret().setUserId(userId);
        secret.add(signUpInfo.getSecret());
        return true;
    }
}
