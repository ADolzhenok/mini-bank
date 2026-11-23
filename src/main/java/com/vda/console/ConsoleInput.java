package com.vda.console;

import com.vda.operations.ConsoleOperationType;
import com.vda.operations.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConsoleInput {

    private final static Map<ConsoleOperationType, OperationCommand> operations
            = new HashMap<>();

    public ConsoleInput(List<OperationCommand> operationList){
        operationList.forEach( command ->
                operations.put(command.type(), command)
        );
    }

    public void run(){
        System.out.println("MiniBank is started. Type EXIT to stop.");
        System.out.println("Available commands: " +
                Arrays.stream(ConsoleOperationType.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")));

        try(Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("Enter a command:");

                String inputtedCommand = normalizeCommand(sc.nextLine());

                try {
                    ConsoleOperationType type = ConsoleOperationType.valueOf(inputtedCommand);

                    if (type == ConsoleOperationType.EXIT){
                        break;
                    }

                    OperationCommand command = operations.get(type);
                    command.execute(sc);
                }
                catch(IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                    System.out.println("Error: The command is invalid or something wrong happened");
                }
                catch(IllegalStateException ex){
                    System.out.println(ex.getMessage());
                }
                catch(Exception ex){
                    System.out.println("Unexpected error. Try again");
                }
            }
            System.out.println("MiniBank stopped.");
        }
    }

    private String normalizeCommand(String inputtedCommand){
        return inputtedCommand.trim()
                .toUpperCase()
                .replace(" ", "_");
    }

}
