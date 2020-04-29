package ua.kpi.iasa;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Demo {

    public static int THREADS_NUMBER = 5;

    public static void main(String[] args) {

        File[] files = Utils.getFiles();

        final int SIZE = files.length;

        Map<Integer,File> fileIndex = new HashMap<>();
        HashMap<String, HashSet<Integer>> invertedIndex = new HashMap<>();

        InvertedIndexCalcThread[] invertedIndexCalcThreads = new InvertedIndexCalcThread[THREADS_NUMBER];

        for(int threadN = 0; threadN < THREADS_NUMBER; threadN++){
            invertedIndexCalcThreads[threadN] = new InvertedIndexCalcThread(
                    Arrays.copyOfRange(files,
                                    SIZE/THREADS_NUMBER * threadN,
                                            threadN == (THREADS_NUMBER - 1)
                                            ?SIZE
                                            :SIZE/THREADS_NUMBER * (threadN + 1)),
                    SIZE/THREADS_NUMBER * threadN);

            invertedIndexCalcThreads[threadN].start();
        }

        for(int threadN = 0; threadN < THREADS_NUMBER; threadN++){
            try {
                invertedIndexCalcThreads[threadN].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int threadN = 0; threadN < THREADS_NUMBER; threadN++){
            invertedIndexCalcThreads[threadN]
                    .getInvertedIndex()
                    .forEach((k, v) -> invertedIndex.merge(k, v, (v1, v2) -> {
                        HashSet<Integer> set = new HashSet<>(v1);
                        set.addAll(v2);
                        return set;
            }));
            fileIndex.putAll(invertedIndexCalcThreads[threadN].getFileIndex());
        }

        System.out.println(invertedIndex.get("would"));
        System.out.println(invertedIndex.size());
        System.out.println(fileIndex);
    }
}
