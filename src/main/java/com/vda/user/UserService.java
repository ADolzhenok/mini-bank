package com.vda.user;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.*;

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
        List<User> users = session
                .createQuery("FROM User u JOIN FETCH u.accountList", User.class).getResultList();
        return users;
    }

    private boolean validLogin(String login){
        return login.trim().matches("^[a-zA-Zа-яА-Я\\s]+$");
    }

    public User findUserById(Session session, int userId){
        User user = session.find(User.class, userId);

        if (user == null){
            throw new IllegalStateException("The user doesn't exist with this id");
        }
        return user;
    }
}
