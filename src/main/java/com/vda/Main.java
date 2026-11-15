package com.vda;

import com.vda.config.ApplicationConfiguration;
import com.vda.console.ConsoleInput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args){
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ApplicationConfiguration.class)){
            ConsoleInput consoleInput = context.getBean(ConsoleInput.class);
            consoleInput.run();
        }

    }
}
