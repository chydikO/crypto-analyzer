package com.chudik0;

import com.chudik0.service.FileService;
import com.chudik0.util.Command;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    public static void runWithMenu() {
        System.out.println("-= CryptAnalyzer V1.0 =-");
        Scanner scanner = new Scanner(System.in);

        Command command = readCommand(scanner);
        String filePath = readFilePath(scanner);
        int keyOrFilePath = readKey(scanner);

        processCommand(command, filePath, keyOrFilePath);
    }

    private static Command readCommand(Scanner scanner) {
        Command command = null;
        while (command == null) {
            System.out.print("Enter command (ENCRYPT, DECRYPT, BRUTE_FORCE): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                command = Command.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid command. Please enter a valid command.");
            }
        }
        return command;
    }

    private static String readFilePath(Scanner scanner) {
        System.out.print("Enter file path: ");
        return scanner.nextLine().trim();
    }

    private static int readKey(Scanner scanner) {
        String key = "";
        while (key.isEmpty() || isValidKey(key)) {
            System.out.print("Enter key (0 - 50): ");
            key = scanner.nextLine().trim();
            if (isValidKey(key)) {
                System.out.println("Invalid key. Please enter a valid key.");
            }
        }
        return Integer.parseInt(key);
    }

    private static boolean isValidKey(String key) {
        try {
            int keyInt = Integer.parseInt(key);
            return keyInt < 0 || keyInt > 50;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static void processCommand(Command command, String filePath, int key) {
        try {
            String text = FileService.readTextFromFile(filePath);
            String result;
            switch (command) {
                case ENCRYPT:
                    result = CaesarCipher.encrypt(text, key);
                    break;
                case DECRYPT:
                    result = CaesarCipher.decrypt(text, key);
                    break;
                case BRUTE_FORCE:
                    FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
                   result = frequencyAnalyzer.analyzeFrequency(filePath);
                    break;
                default:
                    System.out.println("Invalid command");
                    return;
            }
            String outputFilePath = getOutputFilePath(filePath, command.name());
            FileService.writeTextToFile(outputFilePath, result);
            System.out.println("Operation completed successfully. Result saved to " + outputFilePath);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static String getOutputFilePath(String filePath, String command) {
        int dotIndex = filePath.lastIndexOf('.');
        return filePath.substring(0, dotIndex) + "[" + command + "]" + filePath.substring(dotIndex);
    }
}
