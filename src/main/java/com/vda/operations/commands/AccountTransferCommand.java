package com.vda.operations.commands;

import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountTransferCommand implements OperationCommand {

    private final OperationsConsoleListener operationsConsoleListener;

    public AccountTransferCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

    public void execute(Scanner sc){
        System.out.println("Enter source account id:");
        int sourceAccountId = Integer.parseInt(sc.nextLine());

        System.out.println("Enter target account id:");
        int targetAccountId = Integer.parseInt(sc.nextLine());

        System.out.println("Enter amount:");
        int amount = Integer.parseInt(sc.nextLine());

        try {
            int commissionAmount = operationsConsoleListener.accountTransfer(
                    sourceAccountId, targetAccountId, amount);

            if (commissionAmount == 0){
                System.out.printf(
                        "Transfer completed from account %d to account %d. " +
                                "Amount: %d (no commission, same user)\n",
                        sourceAccountId, targetAccountId, amount);
            }
            else {
                System.out.printf(
                        "Transfer completed from account %d to account %d. " +
                                "Amount: %d, commission: %d, recipient received: %d\n",
                        sourceAccountId, targetAccountId,
                        amount, commissionAmount, amount - commissionAmount);
            }

        }
        catch(IllegalStateException ex){
            System.out.println(ex.getMessage());
        }

    }

}
