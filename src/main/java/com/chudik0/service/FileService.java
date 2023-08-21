package com.chudik0.service;

import java.io.*;

public class FileService {
    public static String readTextFromFile(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
        }
        return text.toString();
    }

    public static void writeTextToFile(String filePath, String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
        }
    }
}
