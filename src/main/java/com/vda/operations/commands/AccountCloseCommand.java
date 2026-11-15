package com.vda.operations.commands;

import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AccountCloseCommand implements OperationCommand {

    private final OperationsConsoleListener operationsConsoleListener;

    public AccountCloseCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }

    public void execute(Scanner sc){
        System.out.println("Enter account id to close:");
        int accountId = Integer.parseInt(sc.nextLine());

        try {
            List<Integer> closedAccAmountAndTargetAccId =
                    operationsConsoleListener.accountClose(accountId);
            System.out.printf(
                    "Account %d closed. Remaining balance %d transferred to account %d.\n",
                    accountId,
                    closedAccAmountAndTargetAccId.getFirst(),
                    closedAccAmountAndTargetAccId.getLast());
        }
        catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }

    }
}
