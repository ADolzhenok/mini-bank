package com.vda.user;

import com.vda.account.AccountService;
import com.vda.utils.TransactionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserService {

    public UserService(){
    }

    public User createUser(Session session, String login){
        if (!validLogin(login)){
            throw new IllegalStateException("The login is incorrect");
        }

        User user = session.createQuery("FROM User u WHERE u.login=:login", User.class)
                            .setParameter("login", login)
                            .uniqueResult();

        boolean userExists = user != null;

        if (userExists){
            throw new IllegalStateException(
                    "The login has already existed. Use another login and try again.");
        }

        User newUser = new User(login);
        session.persist(newUser);

        return newUser;
    }

    public List<User> findAllUsers(Session session){
        return session.createQuery("FROM User", User.class).getResultList();
    }

    private boolean validLogin(String login){
        return login.matches("^[a-zA-Zа-яА-Я\\s]+$");
    }

    public User findUserById(Session session, int userId){
        User user = session.find(User.class, userId);

        if (user == null){
            throw new IllegalStateException("The user doesn't exist with this id");
        }
        return user;
    }
}
