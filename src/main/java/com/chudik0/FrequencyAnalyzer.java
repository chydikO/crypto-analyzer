package com.chudik0;

import com.chudik0.service.FileService;
import com.chudik0.util.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * В этой реализации метод analyzeFrequency выполняет следующие шаги:
 * Считывает зашифрованный текст из файла с помощью метода FileService.readTextFromFile.
 * Вычисляет частоту каждого символа в тексте с помощью метода calculateCharacterFrequency.
 * Находит наиболее часто встречающийся символ с помощью метода findMostFrequentCharacter.
 * Вычисляет ключ для расшифровки текста с помощью метода calculateKey. В данной реализации предполагается, что наиболее часто встречающийся символ в зашифрованном тексте соответствует символу 'e' в исходном тексте.
 * Расшифровывает текст с использованием ключа с помощью метода CaesarCipher.decrypt.
 * Возвращает расшифрованный текст.
 */
public class FrequencyAnalyzer {
    private char decryptedChar;

    public String analyzeFrequency(String filePath) {
        try {
            String encryptedText = FileService.readTextFromFile(filePath);
            Map<Character, Integer> frequencyMap = calculateCharacterFrequency(encryptedText);
            char mostFrequentCharacter = findMostFrequentCharacter(frequencyMap);
            int key = calculateKey(mostFrequentCharacter, decryptedChar); // Здесь 'e' - это наиболее часто встречающийся символ в английском языке
            System.out.println(key);
            String decryptedText = CaesarCipher.decrypt(encryptedText, key);
            return decryptedText;
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return "";
    }

    /**
     * Вычисляет частоту каждого символа в тексте
     * @param text
     * @return
     */
    private Map<Character, Integer> calculateCharacterFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                List<Character> alphabet = getAlphabet(ch);
                if (alphabet != null) {
                    frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
                }
            }
        }
        return frequencyMap;
    }

    /**
     * Находит наиболее часто встречающийся символ
     * @param frequencyMap
     * @return
     */
    private char findMostFrequentCharacter(Map<Character, Integer> frequencyMap) {
        char mostFrequentCharacter = ' ';
        int maxFrequency = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentCharacter = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mostFrequentCharacter;
    }

    /**
     *
     * Вычисляет ключ для расшифровки текста
     * @param encryptedChar
     * @param decryptedChar
     * @return
     */
    private int calculateKey(char encryptedChar, char decryptedChar) {
        List<Character> alphabet = getAlphabet(encryptedChar);
        if (alphabet != null) {
            int encryptedCharIndex = alphabet.indexOf(encryptedChar);
            int decryptedCharIndex = alphabet.indexOf(decryptedChar);
            int key = (encryptedCharIndex - decryptedCharIndex) % alphabet.size();
            if (key < 0) {
                key += alphabet.size();
            }
            if (alphabet.equals(Constants.UKRAINIAN_ALPHABET)) {
                key += 1;
            }
            return key;
        }
        return 0;
    }

    private List<Character> getAlphabet(char ch) {
        if (Constants.ENGLISH_ALPHABET.contains(ch)) {
            decryptedChar = 'e';
            return Constants.ENGLISH_ALPHABET;
        } else if (Constants.UKRAINIAN_ALPHABET.contains(ch)) {
            decryptedChar = 'о';
            return Constants.UKRAINIAN_ALPHABET;
        }
        return null;
    }
}

  /*  private int calculateKey(char encryptedChar, char decryptedChar) {
        List<Character> alphabet = getAlphabet(encryptedChar);
        if (alphabet != null) {
            int encryptedCharIndex = alphabet.indexOf(Character.toLowerCase(encryptedChar));
            int decryptedCharIndex = alphabet.indexOf(Character.toLowerCase(decryptedChar));
            int key = (encryptedCharIndex - decryptedCharIndex) % alphabet.size();
            if (key < 0) {
                key += alphabet.size();
            }
            return key;
        }
        return 0;
    }*/