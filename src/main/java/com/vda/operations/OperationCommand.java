package com.vda.operations;

import java.util.Scanner;

public interface OperationCommand {
    ConsoleOperationType type();
    void execute(Scanner scanner);

}
