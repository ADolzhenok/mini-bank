package com.vda.account;

import com.vda.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AccountService {
    private static final Map<Integer, Account> accountMap = new HashMap<>();
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties){
        this.accountProperties = accountProperties;
    }

    public Account createAccount(User user){

        int accountId = accountMap.size() + 1;
        Account newAccount = new Account(
                accountId,
                user.getId(),
                accountProperties.getDefaultAmount()
        );
        accountMap.put(accountId, newAccount);
        user.getAccountList().add(newAccount);

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
        int commissionAmount = 0;
        if (sourceAccount.getUserId() == targetAccount.getUserId()){
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

    public List<Integer> accountClose(User user, Account accountToClose){

        List<Account> userAccounts = user.getAccountList().stream()
                .filter(account ->
                        account.getUserId() == accountToClose.getUserId() &&
                        account.getId() != accountToClose.getId())
                .collect(Collectors.toList());

        if (userAccounts.isEmpty()){
            throw new IllegalStateException(
                    "Cannot delete the account because the user has no other accounts.");
        }

        Account targetAccount = userAccounts.getFirst();

        List<Integer> closedAccAmountAndTargetAccId = new ArrayList<>();
        closedAccAmountAndTargetAccId.add(accountToClose.getMoneyAmount());
        closedAccAmountAndTargetAccId.add(targetAccount.getId());

        accountTransfer(accountToClose, targetAccount, accountToClose.getMoneyAmount());

        user.setAccountList(userAccounts);
        accountMap.remove(accountToClose.getId());

        return closedAccAmountAndTargetAccId;
    }

    public Account findAccountById(int accountId){
        if (!accountMap.containsKey(accountId)){
            throw new IllegalStateException(
                    "The account doesn't exist with this id=" + accountId
            );
        }
        return accountMap.get(accountId);
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
