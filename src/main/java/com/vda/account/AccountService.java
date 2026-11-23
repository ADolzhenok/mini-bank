package com.vda.account;

import com.vda.user.User;
import com.vda.utils.TransactionHelper;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AccountService {
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties){
        this.accountProperties = accountProperties;
    }

    public Account createAccount(Session session, User user){
        Account newAccount = new Account(
                accountProperties.getDefaultAmount(),
                user
        );
        session.persist(newAccount);

        return newAccount;
    }

    public Account accountDeposit(Account account, int amount){
        validAmount(amount);

        account.setMoneyAmount(account.getMoneyAmount() + amount);
        return account;
    }

    public Account accountWithdraw(Account account, int amount){
        validAmount(amount);
        validWithdraw(account, amount);

        account.setMoneyAmount(account.getMoneyAmount() - amount);
        return account;
    }

    public int accountTransfer(Account sourceAccount, Account targetAccount, int amount){
        if (sourceAccount.getId() == targetAccount.getId()){
            throw new IllegalStateException("transferring between the same account is not allow");
        }

        int commissionAmount = 0;
        if (sourceAccount.getUser().getId() == targetAccount.getUser().getId()){
            accountWithdraw(sourceAccount, amount);
            accountDeposit(targetAccount, amount);
        }
        else{
            commissionAmount = (int) (amount * accountProperties.getTransferCommission());
            accountWithdraw(sourceAccount, amount);
            accountDeposit(targetAccount, amount - commissionAmount);
        }

        return commissionAmount;
    }

    public List<Integer> accountClose(Session session, Account accountToClose){

        List<Account> userAccounts = accountToClose.getUser().getAccountList().stream()
                .filter(account ->
                        account.getUser().getId() == accountToClose.getUser().getId() &&
                        account.getId() != accountToClose.getId())
                .sorted(Comparator.comparingInt(Account::getId))
                .toList();

        if (userAccounts.isEmpty()){
            throw new IllegalStateException(
                    "Cannot delete the account because the user has no other accounts.");
        }

        Account targetAccount = userAccounts.getFirst();

        List<Integer> closedAccAmountAndTargetAccId = new ArrayList<>();
        closedAccAmountAndTargetAccId.add(accountToClose.getMoneyAmount());
        closedAccAmountAndTargetAccId.add(targetAccount.getId());

        accountTransfer(accountToClose, targetAccount, accountToClose.getMoneyAmount());

        session.remove(accountToClose);

        return closedAccAmountAndTargetAccId;
    }

    public Account findAccountById(Session session, int accountId){
        Account account = session.find(Account.class, accountId);

        if (account == null){
            throw new IllegalStateException(
                    "The account doesn't exist with this id=" + accountId
            );
        }
        return account;
    }

    private void validAmount(int amount){
        if (amount <= 0){
            throw new IllegalStateException("The amount=" + amount +" must be positive");
        }
    }

    private void validWithdraw(Account account, int withdrawalAmount){
        if (account.getMoneyAmount() < withdrawalAmount){
            String msg = String.format(
                    "insufficient funds on account id=%d, moneyAmount=%d, attempted withdraw=%d",
                    account.getId(), account.getMoneyAmount(), withdrawalAmount);
            throw new IllegalStateException(msg);
        }
    }

}
