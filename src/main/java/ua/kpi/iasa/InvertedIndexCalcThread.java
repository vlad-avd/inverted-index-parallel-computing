package ua.kpi.iasa;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InvertedIndexCalcThread extends Thread {

    private final File[] files;
    private int idx;
    public Map<Integer,File> fileIndex;
    public HashMap<String, HashSet<Integer>> invertedIndex;

    public Map<Integer, File> getFileIndex() {
        return fileIndex;
    }

    public HashMap<String, HashSet<Integer>> getInvertedIndex() {
        return invertedIndex;
    }

    {
        fileIndex = new HashMap<>();
        invertedIndex = new HashMap<>();
    }

    public InvertedIndexCalcThread(File[] files, int idx) {
        this.files = files;
        this.idx = idx;
    }

    @Override
    public void run() {
        for(File fileName : files){

            try(BufferedReader file = new BufferedReader(new FileReader(fileName)))
            {
                fileIndex.put(idx,fileName);
                String line;

                while( (line = file.readLine()) != null) {

                    line = line.replaceAll("<br />", " ")
                            .replaceAll("\\W", " ")
                            .replaceAll(" +", " ")
                            .trim()
                            .toLowerCase();

                    String[] words = line.split(" ");

                    for(String word:words){

                        if (!invertedIndex.containsKey(word)) {
                            invertedIndex.put(word, new HashSet<Integer>());
                        }

                        invertedIndex.get(word).add(idx);
                    }
                }
            } catch (IOException e){
                System.out.println("File: " + fileName + " not found.");
            }
            idx++;
        }
    }
}
