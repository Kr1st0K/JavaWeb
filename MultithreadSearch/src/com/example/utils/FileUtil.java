package com.example.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static final String[] textFileExtensions = {
            ".txt", ".js", ".css", ".java", ".cs", ".py", ".xaml", ".yaml", ".yml",
            ".gitignore", ".xml", ".asm", ".html", ".iml", ".log", ".csv", ".json"
    };

    public static boolean isTextFile(String filepath) {
        for (String textFileExtension : textFileExtensions)
            if (filepath.endsWith(textFileExtension))
                return true;
        return false;
    }

    public static void createFile(String filepath) throws IOException {
        File file = new File(filepath);
        file.createNewFile();
    }

    public static void writeData(String filepath, List<String> whatToWrite) throws IOException {
        FileUtil.createFile(filepath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String str : whatToWrite)
                bw.append(str).append("\n");
        }
    }

    public static List<String> search(String filepath, String searchPhrase) throws IOException {
        List<String> result = new ArrayList<>();
        String str;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while ((str = br.readLine()) != null) {
                String[] parts = str.split("[ .!?,:;\\-]");
                if (Arrays.asList(parts).contains(searchPhrase)) {
                    result.add(filepath + ": " + str);
                }
            }
        }
        return result;
    }
}