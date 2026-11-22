package com.vda.console;

import com.vda.account.Account;
import com.vda.account.AccountService;
import com.vda.utils.TransactionHelper;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import com.vda.user.User;
import com.vda.user.UserService;

import java.util.List;

@Component
public class OperationsConsoleListener {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionHelper transactionHelper;

    public OperationsConsoleListener(
            UserService userService,
            AccountService accountService,
            TransactionHelper transactionHelper
    ){
        this.userService = userService;
        this.accountService = accountService;
        this.transactionHelper = transactionHelper;
    }

    public User createUser(String login) {
        return transactionHelper.executeTransaction( session -> {
            User user = userService.createUser(session, login);
            accountService.createAccount(session, user);
            session.refresh(user);
            return user;
        });
    }

    public List<User> findAllUsers() {
        return transactionHelper.executeTransaction(userService::findAllUsers);
    }

    public Account createAccount(int userId){
        return transactionHelper.executeTransaction(session -> {
            User user = userService.findUserById(session, userId);
            return accountService.createAccount(session, user);
        });

    }

    public Account accountDeposit(int accountId, int amount){
        return transactionHelper.executeTransaction(session -> {
            Account account = accountService.findAccountById(session, accountId);
            return accountService.accountDeposit(account, amount);
        });

    }

    public Account accountWithdraw(int accountId, int amount){
        return transactionHelper.executeTransaction(session -> {
            Account account = accountService.findAccountById(session, accountId);
            return accountService.accountWithdraw(account, amount);
        });
    }

    public int accountTransfer(int sourceAccountId, int targetAccountId, int amount){
        return transactionHelper.executeTransaction(session -> {
            Account sourceAccount = accountService.findAccountById(session, sourceAccountId);
            Account targetAccount = accountService.findAccountById(session, targetAccountId);
            return accountService.accountTransfer(sourceAccount, targetAccount, amount);
        });

    }

    public List<Integer> accountClose(int accountId){
        return transactionHelper.executeTransaction(session -> {
            Account accountToClose = accountService.findAccountById(session, accountId);
            User user = userService.findUserById(session, accountToClose.getUser().getId());

            return accountService.accountClose(session, user, accountToClose);
        });

    }


}
