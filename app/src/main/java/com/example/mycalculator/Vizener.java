package com.example.mycalculator;
import android.opengl.Visibility;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class Vizener
{
    public static int smesh = (int)'a';//смещение алфавита относительно таблицы юникодов english
    public static int bias = 1072, letters = 33;

    public static String encrypt(String text, String keyWord)
    {
        keyWord = Vizener.generateKey(text, keyWord);
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < text.length();i++)
        {
            char c;
            if (text.charAt(i) >= 'a' && text.charAt(i) <= 'z'){
                int num = ((text.charAt(i) + keyWord.charAt(i % keyWord.length()) - 2 * smesh) % 26);
                c = (char) (num + smesh);
                ans.append(c);
            }else if ((text.charAt(i) >= 'А' && text.charAt(i) <= 'я') || text.charAt(i) == 'ё' || text.charAt(i) == 'Ё' ){
                c = encryptRU(text.charAt(i), i, keyWord);
                ans.append(c);
            }else
            {
                int num = text.charAt(i) + keyWord.length();
                c = (char)num;
                ans.append(c);
            }

        }
        return ans.toString();
    }

    public static String decrypt(String text, String keyWord)
    {
        keyWord = Vizener.generateKey(text, keyWord);
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < text.length(); i++)
        {
            char c;
            if (text.charAt(i) >= 'a' && text.charAt(i) <= 'z'){
                int num = ((text.charAt(i) - keyWord.charAt(i % keyWord.length()) + 26) % 26);
                c = (char) (num + smesh);
                ans.append(c);
            }else if ((text.charAt(i) >= 'А' && text.charAt(i) <= 'я') || text.charAt(i) == 'ё' || text.charAt(i) == 'Ё'){
                c = decryptRU(text.charAt(i), i, keyWord);
                ans.append(c);
            }else
            {
                int num = text.charAt(i) - keyWord.length();
                c = (char)num;
                ans.append(c);
            }
        }
        return ans.toString();
    }

    public static char encryptRU(char bukva, int i, String keyWord)
    {
        char encryptRU;
        final int keyLen = keyWord.length();
        if (Character.isUpperCase(bukva) == false)
        {
            encryptRU = toLowerCase((char) (((bukva + keyWord.charAt(i % keyLen) - 2 * bias) % letters) + bias));
        }
        else
        {
            int num = bukva + keyWord.length();
            encryptRU = (char)num;
        }
        return encryptRU;
    }

    public static char decryptRU(char bukva, int i, String keyWord)
    {
        char decryptRU;
        final int keyLen = keyWord.length();
        if (Character.isUpperCase(bukva) == false)
        {
            bukva = toUpperCase(bukva);
            decryptRU = (char) (((bukva - keyWord.charAt(i % keyLen) + letters) % letters) + bias);
        }
        else
        {
            int num = bukva - keyWord.length();
            decryptRU = (char)num;
        }
        return decryptRU;
    }
    static String generateKey(String str, String key)
    {
        int x = str.length();

        for (int i = 0; ; i++)
        {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }
}

