package com.chudik0;

import com.chudik0.util.Constants;
import java.util.List;

public class CaesarCipher {

    public static String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            List<Character> alphabet = getAlphabet(ch);
            if (alphabet != null) {
                char encryptedChar;
                int encryptCharPosition = alphabet.indexOf(ch);
                int newEncryptCharPosition = (encryptCharPosition + key) % alphabet.size();
                encryptedChar = alphabet.get(newEncryptCharPosition);
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(ch);
            }
        }
        return encryptedText.toString();
    }

    public static String decrypt(String text, int key) {
        StringBuilder decryptedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            List<Character> alphabet = getAlphabet(ch);
            if (alphabet != null) {
                char decryptedChar;
                int encryptCharPosition = alphabet.indexOf(ch);

                int sizeAlphabet = alphabet.size();
                int decryptCharPosition = (encryptCharPosition - key + sizeAlphabet) % sizeAlphabet;
                if (decryptCharPosition < 0) {
                    decryptCharPosition += sizeAlphabet;
                }
                decryptedChar = alphabet.get(decryptCharPosition);

                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(ch);
            }
        }
        return decryptedText.toString();
    }

    private static List<Character> getAlphabet(char ch) {
        if (Constants.ENGLISH_ALPHABET.contains(ch)) {
            return Constants.ENGLISH_ALPHABET;
        } else if (Constants.UKRAINIAN_ALPHABET.contains(ch)) {
            return Constants.UKRAINIAN_ALPHABET;
        } else if (Constants.SYMBOLS.contains(ch)) {
            return Constants.SYMBOLS;
        }
        return null;
    }
}