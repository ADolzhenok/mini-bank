package com.vda.console;

import com.vda.account.Account;
import com.vda.account.AccountService;
import org.springframework.stereotype.Component;
import com.vda.user.User;
import com.vda.user.UserService;

import java.util.List;

@Component
public class OperationsConsoleListener {

    private final UserService userService;
    private final AccountService accountService;

    public OperationsConsoleListener(
            UserService userService,
            AccountService accountService
    ){
        this.userService = userService;
        this.accountService = accountService;
    }

    public User createUser(String login) {
        User user = userService.createUser(login);
        accountService.createAccount(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    public Account createAccount(int userId){
        User user = userService.findUserById(userId);
        return accountService.createAccount(user);
    }

    public Account accountDeposit(int accountId, int amount){
        Account account = accountService.findAccountById(accountId);
        return accountService.accountDeposit(account, amount);
    }

    public Account accountWithdraw(int accountId, int amount){
        Account account = accountService.findAccountById(accountId);
        return accountService.accountWithdraw(account, amount);
    }

    public int accountTransfer(int sourceAccountId, int targetAccountId, int amount){
        Account sourceAccount = accountService.findAccountById(sourceAccountId);
        Account targetAccount = accountService.findAccountById(targetAccountId);
        return accountService.accountTransfer(sourceAccount, targetAccount, amount);
    }

    public List<Integer> accountClose(int accountId){
        Account accountToClose = accountService.findAccountById(accountId);
        User user = userService.findUserById(accountToClose.getUserId());

        return accountService.accountClose(user, accountToClose);
    }


}
