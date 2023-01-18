package com.example;

import com.example.multithread.MultiThreadingFileSearcher;
import com.example.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String[] data = getTaskFromScanner();
        if (!data[0].isEmpty() && !data[1].isEmpty() && !data[2].isEmpty()) {
            File file = new File(data[1]);
            file.delete();

            MultiThreadingFileSearcher mfs =
                    new MultiThreadingFileSearcher(data[0], data[2]);

            ForkJoinPool fjp = new ForkJoinPool();
            System.out.println("Start searching\n");
            List<String> result = fjp.invoke(mfs);
            System.out.println("\nFinish searching");
            try {
                FileUtil.writeData(data[1], result);
            } catch (IOException e) {
                System.out.println("There are problems when trying to write in file");
            }
            System.out.println("\n\nResults:");
            result.forEach(System.out::println);
        }
    }

    private static String[] getTaskFromScanner(){
        String[] data = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path to directory where to start search: ");
        data[0] = scanner.next();
        System.out.println("Enter path to file where to store results: ");
        data[1] = scanner.next();
        System.out.println("Enter keyword: ");
        data[2] = scanner.next();
        return data;
    }
}