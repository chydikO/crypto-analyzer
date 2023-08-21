package com.chudik0;

import com.chudik0.util.Command;

public class Runner {
    public static void main(String[] args) {
        if (args.length > 0) {
            run(args);
        } else {
            CLI.runWithMenu();
        }
    }

    public static void run(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar myApp.jar command filePath keyOrFilePath");
            return;
        }

        String commandString = args[0];
        String filePath = args[1];
        String keyString = "0";
        if (args.length == 3) {
            keyString = args[2];
        }

        //TODO: check String commandString = args[0]; what is it in my commands. Test with command prompt
        Command command = null;
        int key = 0;
        try {
            command = Command.valueOf(commandString.toUpperCase());
            key = Integer.parseInt(keyString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid key. Please enter a valid integer key.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command. Please enter a valid command. (ENCRYPT, DECRYPT, BRUTE_FORCE)");
        }
        if (command != null && key != 0) {
            CLI.processCommand(command, filePath, key);
        } else if (command != null && !filePath.isEmpty()) {
            CLI.processCommand(command, filePath, 0);
        }
    }
}
