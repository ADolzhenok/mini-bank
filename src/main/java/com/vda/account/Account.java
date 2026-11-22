package com.vda.account;

import com.vda.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    @Column(name="money_amount", nullable = false)
    int moneyAmount;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Account(){}

    public Account(int moneyAmount, User user){
        this.moneyAmount = moneyAmount;
        this.user = user;
    }

    public int getId(){
        return this.id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", moneyAmount=" + moneyAmount +
                ", user=" + user +  '}';
    }
}
