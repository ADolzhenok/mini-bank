package com.vda.account;

public class Account {

    int id;
    int userId;
    int moneyAmount;

    public Account(int id, int userId, int moneyAmount){
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public int getId(){
        return this.id;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getMoneyAmount(){
        return this.moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
