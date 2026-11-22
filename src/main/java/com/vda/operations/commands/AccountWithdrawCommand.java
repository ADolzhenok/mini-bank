package com.vda.operations.commands;

import com.vda.account.Account;
import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawCommand implements OperationCommand {

    private final OperationsConsoleListener operationsConsoleListener;

    public AccountWithdrawCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }

    public void execute(Scanner sc){
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(sc.nextLine());

        System.out.println("Enter amount:");
        int amount = Integer.parseInt(sc.nextLine());

        try {
            Account account = operationsConsoleListener.accountWithdraw(accountId, amount);
            System.out.printf(
                    "Withdrawn %d to account %d. New balance: %d\n",
                    amount, accountId, account.getMoneyAmount());
        }
        catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }

    }

}
