package com.vda.operations.commands;

import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;
import com.vda.user.User;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {

    private final OperationsConsoleListener operationsConsoleListener;

    public CreateUserCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    @Override
    public ConsoleOperationType type(){
        return ConsoleOperationType.USER_CREATE;
    }

    @Override
    public void execute(Scanner sc){
            while(true) {
                System.out.println("Enter login:");
                String login = sc.nextLine();

                try {
                    User user = operationsConsoleListener.createUser(login);
                    System.out.println("User created:" + user);
                    break;
                }
                catch(IllegalStateException ex){
                    System.out.println(ex.getMessage());
                }
            }


    }
}
