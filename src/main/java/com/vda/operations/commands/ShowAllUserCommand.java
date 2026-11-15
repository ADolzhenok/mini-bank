package com.vda.operations.commands;

import com.vda.console.OperationsConsoleListener;
import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ShowAllUserCommand implements OperationCommand {
    private final OperationsConsoleListener operationsConsoleListener;

    public ShowAllUserCommand(OperationsConsoleListener operationsConsoleListener){
        this.operationsConsoleListener = operationsConsoleListener;
    }

    public ConsoleOperationType type(){
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

    public void execute(Scanner sc){
        operationsConsoleListener.findAllUsers().forEach(System.out::println);
    }
}
