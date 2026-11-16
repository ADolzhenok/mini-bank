package com.vda.operations.commands;

import com.vda.account.Account;
import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountDepositCommand implements OperationCommand {

    private final OperationsConsoleListener operationsConsoleListener;

    public AccountDepositCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }

    public void execute(Scanner sc){
        System.out.println("Enter account id:");
        int accountId = Integer.parseInt(sc.nextLine());

        System.out.println("Enter amount:");
        int amount = Integer.parseInt(sc.nextLine());

        try {
            Account account = operationsConsoleListener.accountDeposit(accountId, amount);
            System.out.printf(
                    "Deposited %d to account %d. New balance: %d\n",
                    amount, accountId, account.getMoneyAmount());
        }
        catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }

    }

}
