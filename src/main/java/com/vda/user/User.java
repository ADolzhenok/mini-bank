package com.vda.user;

import com.vda.account.Account;

import java.util.List;

public class User {
    private int id;
    private String login;
    private List<Account> accountList;

    public User(int id, String login, List<Account> accountList){
        this.id = id;
        this.login = login;
        this.accountList = accountList;
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
