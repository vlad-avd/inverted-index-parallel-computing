package ua.kpi.iasa;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InvertedIndexCalcThread extends Thread {

    private File[] files;
    private int idx;
    public Map<Integer,File> fileIndex;
    public HashMap<String, HashSet<Integer>> invertedIndex;

    public Map<Integer, File> getSources() {
        return fileIndex;
    }

    public HashMap<String, HashSet<Integer>> getIndex() {
        return invertedIndex;
    }

    {
        fileIndex = new HashMap<Integer,File>();
        invertedIndex = new HashMap<String, HashSet<Integer>>();
    }

    public InvertedIndexCalcThread(File[] files, int idx) {

        this.files = files;
        this.idx = idx;
    }

    @Override
    public void run() { // TODO: delete service symbols and tags
        for(File fileName : files){

            try(BufferedReader file = new BufferedReader(new FileReader(fileName)))
            {
                fileIndex.put(idx,fileName);
                String ln;

                while( (ln = file.readLine()) !=null) {

                    String[] words = ln.split(" ");

                    for(String word:words){

                        word = word.toLowerCase();

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
