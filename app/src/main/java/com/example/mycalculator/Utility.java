package com.example.mycalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;

public class Utility {
    public static Boolean isDeleteFile = true;
    public static String key = "bananas";
    public static String keyBase = "29B3D2FE4F79426DBF14338FA72F048F";
    private static Boolean isCompress = false;
    private static Integer selectAlgorithm = 0;
    /*Algorithms:
     * 0 - Шифрование с помощью встроенной библиотеки
     * 1 - Шифрование Виженера
     * */

    public static String storage = "storage.txt";
    public static String storageMap = "storage-map.txt";//хранение последовательности из алг Хоффмана
    public static String storageNotEncrypted = "storage_not_encrypt.txt";

    public static String converterDuration(long duration) {
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        String converted = String.format("%d:%02d", minutes, seconds);
        return converted;
    }

    public static ArrayList<Post> deletePostInList(Context context, int position) throws IOException, JSONException {
        ArrayList<Post> array = getPostsList(context);
        array.remove(position);
        savePostsInFile(context, array);
        return array;
    }

    public static ArrayList<Post> savePostInList(Context context, Post post) throws IOException, JSONException {
        ArrayList<Post> array = getPostsList(context); // получение списка постов из файла
        array.add(post);  // добавление в общий список только что созданного поста
        savePostsInFile(context, array); // сохранение списка постов в файл
        return array;
    }
    public static ArrayList<Post> savePostInList(Context context, Post post, Integer position) throws IOException, JSONException {
        ArrayList<Post> array = getPostsList(context); // получение списка постов из файла
        array.set(position, post);  // добавление в общий список только что созданного поста
        savePostsInFile(context, array); // сохранение списка постов в файл
        return array;
    }
    public static ArrayList<Post> getPostsList(Context context) throws IOException, JSONException { // получение списка постов из файла
        ArrayList<Post> postsList = new ArrayList<Post>();
        File file = new File(context.getFilesDir(), Utility.storage);
        try {
            FileInputStream inputStream = new FileInputStream(file);//
            byte[] inputBytes = new byte[(int) file.length()];      //  - Подучение байтов из файла с данными
            inputStream.read(inputBytes);                           //

            String response = new String(inputBytes);
            if (response.isEmpty()) {
                return postsList;
            }
            if (Utility.isCompress) {
                inputBytes = uncompress(inputBytes, context); // разархивация байтов из файла методом Хоффмана
                response = new String(inputBytes);
            } else {
                response = Utility.deCrypto(inputBytes); // декодирование байтов одним из указанных в Utility.selectAlgorithm методов
            }
            postsList = Utility.convertFromJsonString(response, context); // получение списка постов из декодированной (или разархивированной) строки
            // через json

            inputStream.close();
            return postsList;

        } catch (IOException e) {
            return postsList;
        }

    }


    public static void savePostsInFile(Context context, ArrayList<Post> array) throws JSONException, IOException {
        String userString = convertToJsonString(array, context); // конвертация списка постов в строку через json
        File file = new File(context.getFilesDir(), Utility.storage); // - создание исходного файла
        FileOutputStream outputStream = new FileOutputStream(file);   //
        byte[] outputBytes;
        if (Utility.isCompress) {
            outputBytes = Utility.compress(userString.getBytes(), context); // сжатие данных методом Хоффмана
        } else {
            outputBytes = Utility.enCrypto(userString); // шифрование строки одним из указанных в Utility.selectAlgorithm методов
        }
        outputStream.write(outputBytes); // - запись зашифрованный (или сжатых) байтов в файл
        outputStream.close();            // - закрытие файла
        Utility.saveNotDecrypt(context, userString); // запись исходно (не сжатой и не шифрованной) строки в файл Utility.storageNotEncrypted
    }

    private static byte[] compress(byte[] inputBytes, Context context) {
        Map<Character, Integer> charFreq = Huffman.CharacterFrequency(new String(inputBytes));
        Huffman huffman = Huffman.Create(charFreq);
        try {
            Utility.WriteObjectToFile(context, charFreq);
        } catch (Exception e) {

        }

        return huffman.Encode(new String(inputBytes)).toByteArray();
    }

    private static byte[] uncompress(byte[] inputBytes, Context context) {
        try {
            Map<Character, Integer> charFreq = (Map<Character, Integer>) Utility.ReadObjectFromFile(context);
            BitSet bitset = BitSet.valueOf(inputBytes);
            Huffman huffman = Huffman.Create(charFreq);
            return huffman.Decode(bitset).getBytes();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    private static String convertToJsonString(ArrayList<Post> array, Context context) throws JSONException {
        JSONArray ja = new JSONArray();
        for (Post post : array) {
            ja.put(post.toJson(context));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", ja);
        return jsonObject.toString();
    }

    private static ArrayList<Post> convertFromJsonString(String response, Context context) throws JSONException {
        ArrayList<Post> postsList = new ArrayList<Post>();
        JSONObject reader = new JSONObject(response);
        JSONArray array = reader.getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            JSONObject c = array.getJSONObject(i);
            postsList.add(Post.getPostFromJson(c, context));
        }
        return postsList;
    }

    private static void saveNotDecrypt(Context context, String message) {
        try {
            File file = new File(context.getFilesDir(), Utility.storageNotEncrypted);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(message.getBytes());
            outputStream.close();
        } catch (Exception e) {
        }
    }

    public static void WriteObjectToFile(Context context, Object _object) throws IOException {
        File file = new File(context.getFilesDir(), Utility.storageMap);
        FileOutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objOut = new ObjectOutputStream(outputStream);
        objOut.writeObject(_object);
        objOut.close();
    }

    public static Object ReadObjectFromFile(Context context) throws IOException, ClassNotFoundException {
        File file = new File(context.getFilesDir(), Utility.storageMap);
        FileInputStream inputStream = new FileInputStream(file);
        ObjectInputStream objOut = new ObjectInputStream(inputStream);
        Object obj = objOut.readObject();
        objOut.close();
        return obj;
    }

    private static byte[] enCrypto(String message) {
        byte[] result;
        switch (Utility.selectAlgorithm) {
            case 0:
                result = BaseCrypto.encryptMessageBase(message.getBytes(), Utility.keyBase.getBytes());
                break;
            case 1:
                result = Vizener.encrypt(message, Utility.key).getBytes();
                break;
            default:
                result = BaseCrypto.encryptMessageBase(message.getBytes(), Utility.keyBase.getBytes());
                break;

        }
        return result;
    }

    private static String deCrypto(byte[] inputBytes) {
        String result;
        switch (Utility.selectAlgorithm) {
            case 0:
                result = BaseCrypto.decryptMessageBase(inputBytes, Utility.keyBase.getBytes());
                break;
            case 1:
                result = Vizener.decrypt(new String(inputBytes), Utility.key);
                break;
            default:
                result = BaseCrypto.decryptMessageBase(inputBytes, Utility.keyBase.getBytes());
                break;

        }
        return result;
    }


}

