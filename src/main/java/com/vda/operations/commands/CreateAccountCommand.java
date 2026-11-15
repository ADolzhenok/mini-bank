package com.vda.operations.commands;

import com.vda.account.Account;
import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand{

    private final OperationsConsoleListener operationsConsoleListener;

    public CreateAccountCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.ACCOUNT_CREATE;
    }

    public void execute(Scanner sc){
        System.out.println("Enter user id:");
        int userId = Integer.parseInt(sc.nextLine());

        try {
            Account account = operationsConsoleListener.createAccount(userId);
            System.out.println("Account created: " + account);
        }
        catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }

    }
}
