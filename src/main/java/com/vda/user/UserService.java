package com.vda.user;

import com.vda.account.AccountService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserService {
    private static final Map<Integer, User> userMap = new HashMap<>();

    public UserService(){
    }

    public User createUser(String login){
        if (!validLogin(login)){
            throw new IllegalStateException("The login is incorrect");
        }
        else {
            login = normalizeLogin(login);
        }

        if (userMap.containsKey(login)){
            throw new IllegalStateException(
                    "The login has already existed. Use another login and try again.");
        }

        int userId = userMap.size() + 1;
        User newUser = new User(
                userId,
                login,
                new ArrayList<>()
        );
        userMap.put(userId, newUser);

        return newUser;
    }

    public List<User> findAllUsers(){
        return new ArrayList<>(userMap.values());
    }

    private boolean validLogin(String login){
        return login.matches("^[a-zA-Zа-яА-Я\\s]+$");
    }

    private String normalizeLogin(String login){
        return Arrays.stream(login.trim().toLowerCase().split("\\s+"))
                .map(word ->
                        Character.toUpperCase(word.charAt(0)) +
                                word.substring(1)
                )
                .collect(Collectors.joining(" "));
    }

    public User findUserById(int userId){
        if (!userMap.containsKey(userId)){
            throw new IllegalStateException("The user doesn't exist with this id");
        }
        return userMap.get(userId);
    }
}
