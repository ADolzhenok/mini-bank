package com.vda.user;

import com.vda.account.Account;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name="login", nullable = false, unique = true)
    private String login;
    @OneToMany(mappedBy="user")
    private List<Account> accountList;

    public User(){}

    public User(String login){
        this.login = login;
    }

    public int getId(){
        return this.id;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return this.login;
    }

    public void setAccountList(List<Account> accountList){
        this.accountList = accountList;
    }

    public List<Account> getAccountList(){
        return this.accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
