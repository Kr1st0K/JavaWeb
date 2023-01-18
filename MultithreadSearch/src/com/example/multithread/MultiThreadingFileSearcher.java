package com.example.multithread;

import com.example.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class MultiThreadingFileSearcher extends RecursiveTask<List<String>> {
    private List<File> filesToProcess;
    private final String keyword;

    private MultiThreadingFileSearcher(List<File> files, String keyword) {
        this.filesToProcess = files;
        this.keyword = keyword;
    }

    public MultiThreadingFileSearcher(String srcFilepath, String keyword) {
        this.filesToProcess = new ArrayList<>();
        this.filesToProcess.add(new File(srcFilepath));
        this.keyword = keyword;
    }

    private List<File>[] divideFiles() {
        List<File>[] dividedFiles = new ArrayList[2];
        for (int i = 0; i < dividedFiles.length; i++) {
            dividedFiles[i] = new ArrayList<>();
        }
        if (filesToProcess.size() == 1) {
            if (filesToProcess.get(0).listFiles() == null)
                return dividedFiles;
            filesToProcess = Arrays.stream(filesToProcess.get(0).listFiles())
                    .collect(Collectors.toList());
        }
        int size = filesToProcess.size();
        int partsSize = size / 2;
        for (int i = 0; i < partsSize; i++) {
            dividedFiles[0].add(filesToProcess.get(i));
        }
        for (int i = partsSize; i < size; i++) {
            dividedFiles[1].add(filesToProcess.get(i));
        }

        return dividedFiles;
    }

    @Override
    protected List<String> compute() {
        List<String> result = new ArrayList<>();
        if (filesToProcess.size() == 0 ||
                (filesToProcess.size() == 1 && filesToProcess.get(0).isFile())) {
            if (filesToProcess.size() == 1 &&
                    FileUtil.isTextFile(filesToProcess.get(0).getAbsolutePath())) {
                try {
                    result.addAll(FileUtil.search(
                            filesToProcess.get(0).getAbsolutePath(), keyword));
                } catch (IOException e) {
                    System.out.println("Input/output problems with file \"" +
                            filesToProcess.get(0).getAbsolutePath() + "\"");
                }
            }
        } else {
            List<File>[] dividedFiles = divideFiles();
            MultiThreadingFileSearcher mfsLeft = new MultiThreadingFileSearcher(
                    dividedFiles[0], keyword);
            MultiThreadingFileSearcher mfsRight = new MultiThreadingFileSearcher(
                    dividedFiles[1], keyword);
            mfsLeft.fork();
            List<String> rightResult = mfsRight.compute();
            List<String> leftResult = mfsLeft.join();
            result.addAll(leftResult);
            result.addAll(rightResult);
        }
        return result;
    }
}
